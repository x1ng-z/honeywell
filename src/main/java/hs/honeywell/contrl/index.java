package hs.honeywell.contrl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hs.honeywell.honeywellconnect.DCS;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/12/15 15:02
 */
@Controller
@RequestMapping("/honeywell")
public class index {
    private Logger logger = LoggerFactory.getLogger(index.class);
    @Autowired
    @NonNull
    private DCS dcs;


    @RequestMapping("/")
    @ResponseBody
    public String datadeal(@RequestBody String readcontext) {
        JSONObject parsecontext = JSONObject.parseObject(readcontext);
        String method = parsecontext.getString("method");
        if (method.equals("read")) {
            return read(readcontext);
        } else if (method.equals("write")) {
            return write(readcontext);
        }
        JSONObject result = new JSONObject();
        result.put("msg", "failed");
        return result.toJSONString();
    }


    private String read(String readcontext) {
        logger.info("the read conext is " + readcontext);
        JSONObject result = new JSONObject();
        try {
            JSONObject parsecontext = JSONObject.parseObject(readcontext);

            String method = parsecontext.getString("method");
            if (method.equals("read")) {
                JSONArray tags = parsecontext.getJSONArray("tags");
                List<String> unregistertags = new ArrayList<>();
                List<String> validetags = new ArrayList<>();
                for (int index = 0; index < tags.size(); index++) {
                    JSONObject tag = (JSONObject) tags.get(index);

                    if (!dcs.istagregistered(tag.getString("tag"))) {
                        unregistertags.add(tag.getString("tag"));
                    }
                    validetags.add(tag.getString("tag"));
                }
                if (unregistertags.size() != 0) {
                    dcs.registeritem(unregistertags);
                }
                result.put("data", dcs.getspecialItem(validetags));
                result.put("msg", "success");
            } else {
                result.put("msg", "failed");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("msg", "failed");
        }

        return result.toJSONString();
    }


    private String write(String readcontext) {
        logger.info("the write conext is " + readcontext);
        JSONObject result = new JSONObject();
        try {
            JSONObject parsecontext = JSONObject.parseObject(readcontext);

            String method = parsecontext.getString("method");
            if (method.equals("write")) {
                JSONArray tags = parsecontext.getJSONArray("tags");
                Map<String, String> validetags = new HashMap<>();
                for (int index = 0; index < tags.size(); index++) {
                    JSONObject tag = (JSONObject) tags.get(index);
                    validetags.put(tag.getString("tag"), tag.getDoubleValue("value") + "");
                }
                if (dcs.writespecilaItem(validetags)) {
                    result.put("msg", "success");
                } else {
                    result.put("msg", "failed");
                }
            } else {
                result.put("msg", "failed");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("msg", "failed");
        }

        return result.toJSONString();
    }


}
