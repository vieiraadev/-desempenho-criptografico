import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.util.Base64;

public class CryptoTest {

    private static final String TEXTO = "RSA: algoritmo dos professores do MIT: Rivest, Shamir e Adleman";

    public static void rsa(int bits) throws Exception {
        System.out.println("\n--- RSA " + bits + " bits ---");

        long inicio = System.nanoTime();
        KeyPairGenerator gerador = KeyPairGenerator.getInstance("RSA");
        gerador.initialize(bits);
        KeyPair par = gerador.generateKeyPair();
        long meioChave = System.nanoTime();

        Cipher cifra = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cifra.init(Cipher.ENCRYPT_MODE, par.getPublic());
        byte[] cifrado = cifra.doFinal(TEXTO.getBytes());

        cifra.init(Cipher.DECRYPT_MODE, par.getPrivate());
        byte[] decifrado = cifra.doFinal(cifrado);
        long fim = System.nanoTime();

        System.out.println("Cifrado (Base64): " + Base64.getEncoder().encodeToString(cifrado));
        System.out.println("Decifrado: " + new String(decifrado));

        System.out.printf("Tempo total: %.4f s%n", (fim - inicio) / 1e9);
        System.out.printf("Tempo geração chave: %.4f s%n", (meioChave - inicio) / 1e9);
        System.out.printf("Tempo cifra + decifra: %.4f s%n", (fim - meioChave) / 1e9);
    }

    public static void aes(int bits) throws Exception {
        System.out.println("\n--- AES " + bits + " bits ---");

        KeyGenerator gerador = KeyGenerator.getInstance("AES");
        gerador.init(bits);
        SecretKey chave = gerador.generateKey();

        byte[] iv = new byte[12]; // 96 bits
        SecureRandom rand = new SecureRandom();
        rand.nextBytes(iv);

        Cipher cifra = Cipher.getInstance("AES/GCM/NoPadding");

        long inicio = System.nanoTime();
        cifra.init(Cipher.ENCRYPT_MODE, chave, new GCMParameterSpec(128, iv));
        byte[] cifrado = cifra.doFinal(TEXTO.getBytes());
        long meio = System.nanoTime();

        cifra.init(Cipher.DECRYPT_MODE, chave, new GCMParameterSpec(128, iv));
        byte[] decifrado = cifra.doFinal(cifrado);
        long fim = System.nanoTime();

        System.out.println("Cifrado (Base64): " + Base64.getEncoder().encodeToString(cifrado));
        System.out.println("Decifrado: " + new String(decifrado));

        System.out.printf("Tempo total: %.4f s%n", (fim - inicio) / 1e9);
        System.out.printf("Tempo cifra: %.4f s%n", (meio - inicio) / 1e9);
        System.out.printf("Tempo decifra: %.4f s%n", (fim - meio) / 1e9);
    }

    public static void main(String[] args) throws Exception {
        rsa(1024);
        // rsa(2048);
        // rsa(4096);
        // rsa(8192);
    }
}
