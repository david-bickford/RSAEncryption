
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Random;


/**
 *
 * @author David Bickford
 * @email  drb56@pitt.edu
 */
public class MyKeyGen {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Random rnd = new Random();
        //generates two big integer of size 512
        BigInteger P = BigInteger.probablePrime(512, rnd);
        BigInteger Q = BigInteger.probablePrime(512, rnd);
        while(P.multiply(Q).bitLength() != 1024)
        {
            //while loop to make sure that the bit length of p*q is 1024
            P = BigInteger.probablePrime(512, rnd);
            Q = BigInteger.probablePrime(512, rnd);
        }
        //computs N and PHI
        BigInteger N = P.multiply(Q);
        BigInteger PHI = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));
        
        BigInteger E;
        
        do{
            //makes sure that E is between 1 and Phi and that the GCD of E and PHI is one
            E = BigInteger.probablePrime(1024, rnd);
        }while(BigInteger.ONE.min(E).equals(E) && E.min(PHI).equals(PHI) && !E.gcd(PHI).equals(BigInteger.ONE));
        
        BigInteger D = E.modInverse(PHI);
        //prints out the things needed to the files
        FileOutputStream pub = new FileOutputStream("pubkey.txt");
        ObjectOutputStream pubObj = new ObjectOutputStream(pub);
        FileOutputStream priv = new FileOutputStream("privkey.txt");
        ObjectOutputStream privObj = new ObjectOutputStream(priv);
        pubObj.writeObject(E.toString(10));
        pubObj.writeObject(N.toString(10));
        pubObj.close();
        privObj.writeObject(D.toString(10));
        privObj.writeObject(N.toString(10));
        privObj.close();
    }
}
