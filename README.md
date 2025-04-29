# 🔐 Benchmark de Criptografia AES e RSA em Java

O programa desenvolvido em **Java** que aplica algoritmos de criptografia simétrica (AES) e assimétrica (RSA), com o objetivo de analisar e comparar seus tempos de execução sob diferentes tamanhos de chave.

---

## 📋 Descrição do Projeto

O programa realiza a cifragem do seguinte texto base:

> **“RSA: algoritmo dos professores do MIT: Rivest, Shamir e Adleman”**

E executa benchmarks utilizando os seguintes algoritmos e tamanhos de chave:

### 🔐 Algoritmos e Parâmetros:

- **RSA (chave pública/privada):**
  - 1024 bits
  - 2048 bits
  - 4096 bits
  - 8192 bits

- **AES (chave única):**
  - 128 bits
  - 256 bits

---

## 🛠️ Tecnologias Utilizadas

**Linguagem de Programação:**
- Java

**Bibliotecas Java:**
- `java.security.*` – Geração e gerenciamento de chaves RSA
- `javax.crypto.*` – Algoritmos de criptografia AES
- `java.time.*` – Medição de tempo de execução
- `java.util.Base64` – Codificação de dados criptografados

---

