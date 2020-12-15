package hs.honeywell.test;


import honeywell.HoneyWellServe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    private Logger logger = LoggerFactory.getLogger(test.class);
    static {
        System.loadLibrary("napitst32");
    }
    public static void main(String[] args) {


        HoneyWellServe honeyWellServe=new HoneyWellServe("localhost");//200.0.0.152

        List<String> tagnames=new ArrayList<>();
        tagnames.add("F_TT11.DACA.PV");
        tagnames.add("Q06G_PLJS.zsd.pv");
        honeyWellServe.registerTags(tagnames);

        honeyWellServe.initialTag();


        for(String value :honeyWellServe.getAllTagValue().values()){
            System.out.println(value);
        }

        Map<String,String> writeTag=new HashMap<String,String>();
        writeTag.put("APC_K.FJL_WDOP.PV","1000");
        honeyWellServe.writeTagValues(writeTag);

        for(String value :honeyWellServe.getAllTagValue().values()){
            System.out.println(value);
        }




//        String devicename="F_TT11,Q06G_PLJS".toLowerCase();
//        long[] deviceIds=new long[2];
//        HoneyWellServe honeyWellServe=new HoneyWellServe("localhost");
//        honeyWellServe.getDeviceId("localhost",devicename,2,deviceIds);//localhost
//        for(long value:deviceIds){
//            logger.info("device value is: "+value);
//        }
//
//
//        //long[] devicetoparam=new long[]{0,0};
//        String paramNames="DACA.PV,zsd.pv".toLowerCase();
//        long[] paramIds=new long[]{0,0};
//        honeyWellServe.getDeviceParamterId("localhost",deviceIds,paramNames,2,paramIds);
//        for(long value:paramIds){
//            logger.info("param value is: "+value);
//        }
//
//        logger.info("all value: "+honeyWellServe.getDeviceValues("localhost",deviceIds,paramIds));

    }
}
