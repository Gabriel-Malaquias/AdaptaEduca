<div align="center">
  <h1>🎓 Adapta Educa</h1>
  <p>
    <strong>Plataforma educacional focada na inclusão e acessibilidade de estudantes com deficiência e necessidades específicas no ensino de tecnologia.</strong>
  </p>
</div>

<hr />

<h2>📖 Sobre o Projeto</h2>
<p>
  O <strong>Adapta Educa</strong> é uma solução desenvolvida com o propósito de democratizar o ensino de tecnologia. A plataforma permite que docentes enviem seus planejamentos de aula nos mais diversos formatos (como PDF, DOCX, MP4, entre outros) para que sejam aprimorados e adaptados por Inteligência Artificial, gerando conteúdos acessíveis, intuitivos e estruturados para alunos com deficiência ou com algum grau de dificuldade de aprendizagem.
</p>

<hr />

<h2>🚀 Prévia da Plataforma</h2>

<h3>👨‍🏫 Painel do Docente</h3>
<ul>
  <li>
    <strong>Envio de Materiais:</strong> Funcionalidade simples que permite anexar planejamentos de aula nos mais diversos formatos (PDF, DOCX, MP4, etc.).
  </li>
  <li>
    <strong>Processamento via I.A.:</strong> Integração com a API do <strong>Google Gemini</strong> por meio de <em>prompts</em> pedagógicos pré-definidos e especializados para aprimorar o conteúdo e torná-lo compreensível para estudantes com deficiência.
  </li>
  <li>
    <strong>Validação Pedagógica:</strong> Interface para comparação paralela entre o planejamento original e o material aprimorado pela IA, permitindo a revisão do docente antes do envio final.
  </li>
</ul>

<h3>👨‍🎓 Player do Aluno</h3>
<p>
  Segunda seção da plataforma, projetada de forma simples e intuitiva para que o estudante visualize o conteúdo adaptado pela IA. Conta com ferramentas de usabilidade e acessibilidade para o estudo:
</p>
<ul>
  <li>
    <strong>Alternar para Linguagem Simples:</strong> Permite gerar uma versão ainda mais direta e simplificada do conteúdo adaptado.
  </li>
  <li>
    <strong>Ouvir Áudio Descritivo:</strong> Leitura automatizada em áudio de todo o conteúdo presente na página, ideal para alunos com deficiência visual.
  </li>
  <li>
    <strong>Exibir Tradução em Libras:</strong> Recursos para suporte em Língua Brasileira de Sinais, ideal para alunos com deficiência auditiva.
  </li>
</ul>

<hr />

<h2>⚙️ Arquitetura e Orientações de Execução</h2>
<p>
  A infraestrutura de back-end foi desenvolvida utilizando <strong>Spring Boot</strong> e integrada a um banco de dados em memória (<strong>H2 Database</strong>). Como os serviços de hospedagem estática (como o GitHub Pages) comportam apenas a camada de front-end, é necessário realizar um <em>fork</em> ou clone do repositório para execução em ambiente local, garantindo o funcionamento completo de todas as funcionalidades da aplicação.
</p>

<h3>🔑 Configuração da API do Google Gemini</h3>
<p>
  Por diretrizes de segurança, o arquivo <code>application.properties</code> não expõe a chave de acesso da API do Google Gemini. Para utilizar os recursos de inteligência artificial na plataforma:
</p>
<ol>
  <li>Navegue até o arquivo <code>src/main/resources/application.properties</code>.</li>
  <li>Adicione sua chave da API do Google Gemini (ou de outro modelo desejado) na propriedade correspondente:
    <pre><code>gemini.api.key=SUA_CHAVE_API_AQUI</code></pre>
  </li>
</ol>
<blockquote>
  <p><strong>Nota:</strong> Toda a lógica de integração com a API de IA já se encontra devidamente implementada no back-end do projeto.</p>
</blockquote>

<hr />

<h2>🤝 Como Contribuir</h2>
<p>Se você deseja contribuir para o desenvolvimento do projeto, siga as etapas abaixo:</p>
<ol>
  <li>Faça um <strong>Fork</strong> deste repositório.</li>
  <li>Crie uma nova <em>branch</em> com um nome descritivo para sua contribuição:
    <pre><code>git checkout -b feature/sua-funcionalidade</code></pre>
  </li>
  <li>Faça as alterações necessárias e realize o <em>commit</em>:
    <pre><code>git commit -m "feat: descrição da sua contribuição"</code></pre>
  </li>
  <li>Envie as alterações para o seu repositório remoto:
    <pre><code>git push origin feature/sua-funcionalidade</code></pre>
  </li>
  <li>Abra um <strong>Pull Request (PR)</strong> direcionado à <em>branch</em> principal deste repositório.</li>
</ol>

<hr />

<h2>✉️ Dúvidas e Suporte</h2>
<p>
  Se houver dúvidas, sugestões ou se precisar de ajuda, sinta-se à vontade para abrir uma <em>Issue</em> neste repositório ou entrar em contato com a equipe de desenvolvimento.
</p>
