import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESCipher {

    public static String data = "RSA: algoritmo dos professores do MIT: Rivest, Shamir e Adleman";

    public static class AESResult {
        public String decryptedText;
        public String base64Ciphertext;
        public double executionTime;

        public AESResult(String decryptedText, String base64Ciphertext, double executionTime) {
            this.decryptedText = decryptedText;
            this.base64Ciphertext = base64Ciphertext;
            this.executionTime = executionTime;
        }
    }

    public static AESResult aesCipher(int keySize) throws Exception {
        // Geração da chave AES
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        SecretKey key = keyGen.generateKey();

        // Vetor de inicialização aleatório
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Início da medição do tempo
        long startTime = System.nanoTime();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] ciphertext = cipher.doFinal(data.getBytes());

        Cipher decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = decipher.doFinal(ciphertext);
        String decryptedText = new String(decryptedBytes);

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000_000.0;

        String base64Cipher = Base64.getEncoder().encodeToString(ciphertext);

        return new AESResult(decryptedText, base64Cipher, executionTime);
    }

    public static void main(String[] args) throws Exception {
        AESResult result128 = aesCipher(128);
        AESResult result256 = aesCipher(256);

        System.out.println("--- AES 128 bits ---");
        System.out.println("Texto Cifrado (Base64): " + result128.base64Ciphertext);
        System.out.println("Texto Decifrado: " + result128.decryptedText);
        System.out.printf("Tempo total (cifra + decifra): %.4f s%n", result128.executionTime);

        System.out.println("\n--- AES 256 bits ---");
        System.out.println("Texto Cifrado (Base64): " + result256.base64Ciphertext);
        System.out.println("Texto Decifrado: " + result256.decryptedText);
        System.out.printf("Tempo total (cifra + decifra): %.4f s%n", result256.executionTime);
    }
}
