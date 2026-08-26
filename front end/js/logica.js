const API_BASE_URL = 'http://localhost:8080/api/aulas';

let idAulaAtual = 1;
let tamanhoFonteAtual = 18;
let ehTextoSimplificado = true;

let textoOriginalAtual = "";
let textoSimplificadoAtual = "";

function alternarContraste() {
  document.body.classList.toggle('high-contrast');
}

function alterarTamanhoFonte(delta) {
  tamanhoFonteAtual += delta;
  if (tamanhoFonteAtual < 14) tamanhoFonteAtual = 14;
  if (tamanhoFonteAtual > 28) tamanhoFonteAtual = 28;
  document.body.style.fontSize = tamanhoFonteAtual + 'px';
}

function lerTextoAtivo() {
  const painelAtivo = document.querySelector('.view-panel.active');
  if (!painelAtivo) return;

  const textoParaLer = painelAtivo.innerText;

  if ('speechSynthesis' in window) {
    window.speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(textoParaLer);
    utterance.lang = 'pt-BR';
    window.speechSynthesis.speak(utterance);
  }
}

function alternarVisao(visao) {
  const viewDocente = document.getElementById('view-docente');
  const viewAluno = document.getElementById('view-aluno');
  const tabDocente = document.getElementById('tab-docente');
  const tabAluno = document.getElementById('tab-aluno');

  viewDocente.classList.remove('active');
  viewAluno.classList.remove('active');
  tabDocente.classList.remove('active');
  tabAluno.classList.remove('active');

  if (visao === 'docente') {
    viewDocente.classList.add('active');
    tabDocente.classList.add('active');
  } else {
    viewAluno.classList.add('active');
    tabAluno.classList.add('active');
    
    if (idAulaAtual) {
      buscarAulaPublicada(idAulaAtual);
    }
  }
}

async function processarComIA() {
  const fileInput = document.getElementById('file-input');

  if (!fileInput.files || fileInput.files.length === 0) {
    alert('Por favor, selecione um arquivo (PDF, DOCX, PPTX ou MP4) antes de processar.');
    return;
  }

  const formData = new FormData();
  formData.append('file', fileInput.files[0]);

  try {
    const response = await fetch(`${API_BASE_URL}/upload`, {
      method: 'POST',
      body: formData
    });

    if (!response.ok) throw new Error(`Erro na requisição: ${response.status}`);

    const aulaProcessada = await response.json();

    idAulaAtual = aulaProcessada.id;
    textoOriginalAtual = aulaProcessada.textoOriginal;
    textoSimplificadoAtual = aulaProcessada.textoSimplificado;

    const aiOutputBox = document.getElementById('ai-output');
    if (aiOutputBox) {
      aiOutputBox.innerText = textoSimplificadoAtual;
    }

    alert('⚡ Arquivo processado pela IA com sucesso! O texto adaptado está disponível para revisão.');

  } catch (error) {
    console.warn('Backend offline. Executando em modo de simulação local:', error);
    alert('⚡ [Modo Simulação] IA adaptando material didático para Libras, Áudio e Linguagem Simples... Concluído com sucesso!');
  }
}

async function aprovarConteudo() {
  const aiOutputBox = document.getElementById('ai-output');
  const textoEditadoDocente = aiOutputBox ? aiOutputBox.innerText : textoSimplificadoAtual;

  const payload = {
    textoSimplificado: textoEditadoDocente
  };

  try {
    const response = await fetch(`${API_BASE_URL}/${idAulaAtual}/aprovar`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!response.ok) throw new Error(`Erro ao aprovar aula: ${response.status}`);

    const aulaAprovada = await response.json();
    textoSimplificadoAtual = aulaAprovada.textoSimplificado;

  } catch (error) {
    console.warn('Backend offline. Atualizando dados localmente:', error);
    textoSimplificadoAtual = textoEditadoDocente;
  }

  const studentText = document.getElementById('student-text');
  if (studentText) {
    studentText.innerText = textoSimplificadoAtual;
  }
  ehTextoSimplificado = true;

  alert('✅ Conteúdo aprovado pelo docente! O material agora está disponível no Player do Aluno.');
  alternarVisao('aluno');
}

function alternarLinguagemSimples() {
  const textElem = document.getElementById('student-text');
  if (!textElem) return;

  if (ehTextoSimplificado) {
    textElem.innerText = textoOriginalAtual;
    ehTextoSimplificado = false;
  } else {
    textElem.innerText = textoSimplificadoAtual;
    ehTextoSimplificado = true;
  }
}

function ouvirAudio() {
  const textElement = document.getElementById('student-text');
  if (!textElement) return;

  const text = textElement.innerText;

  if ('speechSynthesis' in window) {
    window.speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(text);
    utterance.lang = 'pt-BR';
    utterance.rate = 0.9;
    window.speechSynthesis.speak(utterance);
  } else {
    alert('O seu navegador não suporta a síntese de voz nativa.');
  }
}

function alternarLibras() {
  const librasBox = document.getElementById('libras-container');
  if (!librasBox) return;

  if (librasBox.style.display === 'none' || librasBox.style.display === '') {
    librasBox.style.display = 'flex';
  } else {
    librasBox.style.display = 'none';
  }
}

async function buscarAulaPublicada(id) {
  try {
    const response = await fetch(`${API_BASE_URL}/${id}`);
    if (response.ok) {
      const aula = await response.json();
      textoOriginalAtual = aula.textoOriginal;
      textoSimplificadoAtual = aula.textoSimplificado;

      const studentText = document.getElementById('student-text');
      if (studentText) {
        studentText.innerText = ehTextoSimplificado ? textoSimplificadoAtual : textoOriginalAtual;
      }
    }
  } catch (error) {
    console.warn('Erro ao buscar dados do servidor:', error);
  }
}