package hs.honeywell.honeywellconnect;

import honeywell.HoneyWellServe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/12/15 14:42
 */
@Component
public class DCS {
    static {
        System.loadLibrary("napitst32");
    }

    private HoneyWellServe honeyWellServe;
    @Value("${dcs.ip}")
    @NonNull
    private String dcsip;

    @PostConstruct
    public void init(){
         honeyWellServe=new HoneyWellServe(dcsip);//200.0.0.152
    }


    public void registeritem(List<String> tags){
        honeyWellServe.registerTags(tags);
        honeyWellServe.initialTag();
    }

    public boolean istagregistered(String tag){
        return honeyWellServe.getTagpool().containsKey(tag);
    }


    public boolean writespecilaItem(Map<String,String> values){
        return honeyWellServe.writeTagValues(values);
    }


    public Map<String,String> getspecialItem(List<String> tags){
        return honeyWellServe.getPartTagValue(tags);
    }









}
