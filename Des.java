
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
 
/**
 * Des加解密工具类
 *
 * @author wz
 */
public class Des {

    static AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实例
    private static SecretKey key = null;

    public Des(String desKey) throws Exception {
        byte[] DESkey = desKey.getBytes();
        byte[] DESIV = {1,2,3,4,5,6,7,8};
        
        DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
        iv = new IvParameterSpec(DESIV);// 设置向量
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
        key = keyFactory.generateSecret(keySpec);// 得到密钥对象
    }
 
    /**
     * 加密
     * @param data 待加密的数据
     * @return 加密后的数据
     * @throws Exception
     */
    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        //BASE64Encoder base64Encoder = new BASE64Encoder();
        return Base64.getEncoder().encodeToString(pasByte);
        //return base64Encoder.encode(pasByte);
    }
 
    /**
     * 解密
     * @param data  解密前的数据
     * @return 解密后的数据
     * @throws Exception
     */
    public String decode(String data) throws Exception {
        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] pasByte = deCipher.doFinal(Base64.getDecoder().decode(data));
        return new String(pasByte, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
    //     String Key = new String();
    //     Scanner sc = new Scanner(System.in);
    //     sc.useDelimiter("/n");
    //     System.out.println("请选择模式:\n 1.加密发出数据包\n 2.解密发出数据包\n 3.解密收到数据包\n 4.计算Remark\n");
    //     String mode = sc.nextLine();
    //     switch (mode) {
    //         case "1":
    //         case "2":
    //             Key = "d4c3b2a1";break;
    //         case "3":
    //             Key = "12345678";break;
    //         case "4":
    //             Key = "PUB_14171";break;
    //         default:
    //             Key = "null";      
    //     }
    //     if(Key == "null") {System.out.println("选择失败~");System.exit(0);}
    //     Des tools = new Des(Key);
    //     while(true){
    //         System.out.println("请输入待处理数据或选择(b->exit c->重新选择模式)");
    //         String data = sc.nextLine();
    //         if(data.equals("b")) System.exit(0);;
    //         if(data.equals("c")){
    //             System.out.println("请选择模式:\n 1.加密发出数据包\n 2.解密发出数据包\n 3.解密收到数据包\n 4.计算Remark\n");
    //             mode = sc.nextLine();
    //         } 
    //         switch(mode){
    //             case "1": System.out.println(tools.encode(data));break;
    //             case "2": System.out.println(tools.decode(data));break;
    //             case "3": System.out.println(tools.decode(data));break;
    //             case "4": System.out.println(tools.encode(data)+"\n");System.out.println(Base62.base62Encode(Base64.getDecoder().decode(tools.encode(data)))+"ie");break;
    //             default: break;
    //         }
    //     }
    }

 
}
