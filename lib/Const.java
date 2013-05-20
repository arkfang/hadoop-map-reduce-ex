
import java.util.ArrayList;
import java.util.HashMap;

public class Const {
    
    public static HashMap<String, Integer> LOGFORMAT;
    
    static {
        
        
        
        // 20   
        // 2012    
        // 22  
        // 49  
        // 481.0   
        // 481.0 
        // 183.4.94.25 
        // 10.10.3.41 
        // guangzhou.anjuke.com 
        // GET
        // /ask/QE5B9BFE5B79EE688BFE5B18BE4B9B0E58D96E4B8ADE4BB8BE8B4B9?from=relevantQuestions 
        // 200 
        // 12888   
        // http://guangzhou.anjuke.com/ask/QE5B9BFE5B79E+E4B8ADE4BB8B+E7A79FE688BF+E4B8ADE4BB8BE8B4B9-D   
        // Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 708; 360SE)  
        // -1  
        // -   
        // 114.80.230.198  
        // F452679C-73FA-1308-75FF-B380CD9D12AB    
        // 204900  
        // 07
        String[] fields = { 
                "hour", "year", "day", "min", "request_time",
                "upstream_response_time", "remote_addr", "upstream_addr",
                "hostname", "method", "request_uri", "http_code", "bytes_sent",
                "referer", "user_agent", "gzip_ratio", "http_x_forwarded_for",
                "server_addr", "guid", "sec", "month" };
        
        LOGFORMAT = new HashMap<String, Integer>();
        for (int i=0; i<fields.length; i++) {
            LOGFORMAT.put(fields[i], i);
        }
    }
    

}
