
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author David Bickford
 * @email drb56@pitt.edu
 */
public class MySign {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        
        //creating messageDigests and reading the file to create the hash
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String file = args[1];
        Path path = Paths.get(file);
        byte[] fileByteArray = Files.readAllBytes(path);
        md.update(fileByteArray);
        byte[] byteData = md.digest();
        BigInteger digest = new BigInteger(1, byteData);
        //getting E, N, and D from pubkey.rsa and privkey.rsa
        FileInputStream pub = new FileInputStream("pubkey.rsa");
        ObjectInputStream pubObj = new ObjectInputStream(pub);
        BigInteger E = new BigInteger((String) pubObj.readObject());
        BigInteger N = new BigInteger((String) pubObj.readObject());
        
        FileInputStream priv = new FileInputStream("privkey.rsa");
        ObjectInputStream privObj = new ObjectInputStream(priv);
        BigInteger D = new BigInteger((String) privObj.readObject());
        
        
        //if user has chosen s
        if(args[0].equals("s")){
            //create the .signed file and prints stuff out
            BigInteger decrypt = digest.modPow(D, N);
            FileOutputStream fos = new FileOutputStream(file + ".signed");
            ObjectOutputStream signed = new ObjectOutputStream(fos);
            signed.writeObject(decrypt);
            signed.writeObject(digest);
        }
        else if(args[0].equals("v")){
            //if user has chosen v
            try{
                //reads from the .signed file
                FileInputStream rsa = new FileInputStream(file);
                ObjectInputStream rsaObj = new ObjectInputStream(rsa);
                //encrypts the object from .signed
                BigInteger dec = (BigInteger)rsaObj.readObject();
                BigInteger blah = (BigInteger)rsaObj.readObject();
                BigInteger encrypt = dec.modPow(E, N);
                if(encrypt.compareTo(blah) == 0){
                    System.out.println("Signature is valid!");
                }
                else{
                    System.out.println("Signature is invalid");
                }
            }catch(Exception e){
                System.out.println("File: " + args[1] + "not found");
            }
        }
        else{
            System.out.println("That isn't a valid input");
        }
    }
}
