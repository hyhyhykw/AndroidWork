package com.it;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;

public class Util {
    /**
     * �����ͻ��˷��͵�json��Ϣ
     * 
     * @param str
     */
    public static void getJson(String str) {
        JSONObject jsonObject = JSONObject.fromObject(str);
        ChatModle modle = new ChatModle();
        modle.setType(jsonObject.getString("type"));
        modle.setChatContent(jsonObject.getString("chatContent"));
        modle.setName(jsonObject.getString("name"));
        System.out.println("type:" + modle.getType());
    }

    /**
     * ���ַ�����֯��json��ʽ���ַ���
     * 
     * @param type
     *        2��ʾ����0��ʾ����
     * @param name
     *        ���߻��������ߵ�����
     * @param chatContent
     *        ��������
     * @return
     */
    public static String getJSonStr(String type, String chatContent, String name) {
        String jsonstr = "";
        try {
            jsonstr = new JSONStringer().object().key("type").value(type)
                .key("chatContent").value(chatContent).key("name").value(name)
                .endObject().toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonstr;

    }

    public static String getPersonalJson(String type, String chatContent,
        String personalName, String name) {
        String jsonstr = "";
        try {
            jsonstr = new JSONStringer().object().key("type").value(type)
                .key("chatContent").value(chatContent).key("personalName")
                .value(personalName).key("name").value(name).endObject()
                .toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonstr;

    }
}
