package com.example.adapta.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.example.adapta.model.Aula;
import com.example.adapta.repository.AulaRepository;


@Service
public class AulaService {
    private final AulaRepository repository;
    
    @Value("${gemini.api.key}")
    private String apiKey;

    public AulaService(AulaRepository repository){
        this.repository = repository;
    }

    public Aula processarEGravarArquivo(MultipartFile file){
        String nomeArquivo = (file != null && file.getOriginalFilename() != null) ? file.getOriginalFilename() : "Aula sem Título";

        String textoOriginal = extrairTextoDoArquivo(file);
        String textoSimplificadoSimulado = gerarLinguagemSimples(textoOriginal);

        Aula novaAula = new Aula(nomeArquivo, textoOriginal, textoSimplificadoSimulado);
        return repository.save(novaAula);
    }

    public String extrairTextoDoArquivo(MultipartFile file){
        try{
            if(file != null && !file.isEmpty()){
                String conteudo = new String(file.getBytes(), StandardCharsets.UTF_8);
                if(!conteudo.isBlank()){
                    return conteudo;
                }
            }
        }catch(Exception e){
            System.out.println("Erro ao ler os bytes do arquivo: " + e.getMessage());
        }

        return "Conteúdo original do arquivo " + (file != null ? file.getOriginalFilename() : "enviado") + ".";
    }

    private String gerarLinguagemSimples(String textoOriginal){
        try {
            String prompt = "Atue como um educador especial. Adapte o seguinte texto para linguagem simples, utilizando frases curtas, voz ativa e vocabulário fácil para alunos com dificuldades de aprendizagem: " + textoOriginal.replace("\"", "'").replace("\n", " ");
            String jsonPayLoad = "{\"contents\":[{\"parts\":[{\"text\":\"" + escapeJson(prompt) + "\"}]}]}";  
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent";
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayLoad))
                .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200){
                System.out.println("Erro HTTP da API Gemini (" + response.statusCode() + ")" + response.body());
                return "Erro de comunicação com a I.A (Status" + response.statusCode() + ")";
            }

            if(response.statusCode() == 503){
                Thread.sleep(2000);
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            
            return extrairTextoDaResposta(response.body());
        } catch (Exception e) {
            System.out.println("Erro na API do GEMINI: " + e.getMessage());
            return "Não foi possível gerar a adaptação no momento";
        }
    }

    public String escapeJson(String input){
        if(input == null) return "";
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }

    public String extrairTextoDaResposta(String jsonResposta){
        try {
            int indexText = jsonResposta.indexOf("\"text\": \"");
            if (indexText != -1) {
                int inicio = indexText + 9;
                StringBuilder sb = new StringBuilder();
                boolean esc = false;
                for (int i = inicio; i < jsonResposta.length(); i++) {
                    char c = jsonResposta.charAt(i);
                    if (esc) {
                        if (c == 'n') sb.append('\n');
                        else if (c == 'r') sb.append('\r');
                        else if (c == 't') sb.append('\t');
                        else sb.append(c);
                        esc = false;
                    } else if (c == '\\') {
                        esc = true;
                    } else if (c == '"') {
                        return sb.toString();
                    } else {
                        sb.append(c);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao realizar a leitura da resposta: " + e.getMessage());
        }
        return jsonResposta;
    }

    public Aula aprovarAula(Long id, String novoTextoSimplificado){
        Aula aula = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Aula não encontrada com ID: " +  id));

        aula.setTextoSimplificado(novoTextoSimplificado);
        aula.setAprovado(true);
        return repository.save(aula);
    }

    public Aula buscarPorId(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Aula não encontrada com o ID: " + id));
    }

    public List<Aula> listarTodas(){
        return repository.findAll();
    }
}
