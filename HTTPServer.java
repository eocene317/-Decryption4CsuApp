import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPServer {
    protected static String replaceBlank(String str){
        String dest = null;
        if(str == null){
            return dest;
        }else{
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            return dest;
        }
    }
    public static void main(String[] args) {
        try {
            ServerSocket ss=new ServerSocket(8889);
            String returnData = new String("");
            while(true){
                Socket socket=ss.accept();
                BufferedReader bd=new BufferedReader(new InputStreamReader(socket.getInputStream()));

                /**
                 * 接受HTTP请求
                 */
                String requestHeader;
                int contentLength=0;
                while((requestHeader=bd.readLine())!=null&&!requestHeader.isEmpty()){
                    // System.out.println(requestHeader);
                    /**
                     * 获得GET参数
                     */
                    if(requestHeader.startsWith("GET")){
                        int begin = requestHeader.indexOf("/?")+2;
                        int end = requestHeader.indexOf("HTTP/");
                        String condition=requestHeader.substring(begin, end);
                        System.out.println("不支持GET方法");

                    }
                    /**
                     * 获得POST参数
                     * 1.获取请求内容长度
                     */
                    if(requestHeader.startsWith("POST")){
                        String mode = "null";
                        String data = "null";
                        String Key = "null";
                        int begin = requestHeader.indexOf("/?")+2;
                        int end = requestHeader.indexOf("HTTP/");
                        String condition=requestHeader.substring(begin, end);
                        String[] conditions = condition.split("&");
                        for(int i=0;i<conditions.length;i++){
                            if(conditions[i].startsWith("mode")) mode = conditions[i].split("=")[1];
                            if(conditions[i].startsWith("data")) data = conditions[i].substring(5,conditions[i].length());
                        }
                        switch (mode) {
                            case "1":
                            case "2":
                                Key = "d4c3b2a1";break;
                            case "3":
                                Key = "12345678";break;
                            case "4":
                                Key = "PUB_14171";break;
                            default:
                                Key = "null";      
                        }
                        data = data.replace("%20", "");
                        data = replaceBlank(data);
                        System.out.println(data);
                        
                        Des tools = new Des(Key);

                       
                        switch(mode){
                            case "1": returnData = tools.encode(data);break;
                            case "2": returnData = tools.decode(data);break;
                            case "3": returnData = tools.decode(data);break;
                            case "4": returnData = Base62.base62Encode(Base64.getDecoder().decode(tools.encode(data)))+"ie";break;
                            default: break;
                        }
                    }
                }
                
                //发送回执
                PrintWriter pw=new PrintWriter(socket.getOutputStream());
                
                pw.println("HTTP/1.1 200 OK");
                pw.println("Content-type:text/html");
                pw.println();
                pw.println(returnData);
                
                pw.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}