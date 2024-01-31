package Utils;

import obj.Doll;
import obj.User;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHandler {

    public static JSONObject jsonFromString(String str) {

        JSONObject jsonObject;

        try {

            jsonObject = new JSONObject(str);
            return jsonObject;

        }catch (Exception e) {

            Log.i("JsonHandler", "jsonFromString", "string is not json");

        }

        return null;

    }

    public static User jsonToUser(JSONObject jsonObject) {

        User user = new User();

        try {

            user.setUserId(jsonObject.getString("userid"));
            user.setPassword(jsonObject.getString("password"));
            user.setIconId(jsonObject.getInt("iconid"));
            user.setName(jsonObject.getString("name"));


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return user;

    }

    public static String userToJsonStr(User user) {

        String str = null;

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", user.getUserId());
            jsonObject.put("name", user.getName());
            jsonObject.put("iconid", user.getIconId());
            str = jsonObject.toString();

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return str;

    }

    public static Doll jsonToDoll(JSONObject jsonObject) {

        Doll doll = new Doll();

        try {

            doll.setName(jsonObject.getString("dollname"));
            doll.setExp(jsonObject.getInt("dollexp"));
            doll.setLevel(jsonObject.getInt("dolllevel"));
            doll.setFrameId(jsonObject.getInt("dollframeid"));
            doll.setBackgroundId(jsonObject.getInt("dollbackgroundid"));
            doll.setImage(jsonObject.getString("dollimage"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return doll;

    }

    public static String dollToJson(Doll doll) {

        String dollJson;

        try {

            if(doll != null) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("datatype", ClientCode.DATATYPE_DOLLDOWN_CODE);
                jsonObject.put("dollname", doll.getName());
                jsonObject.put("dollexp", doll.getExp());
                jsonObject.put("dolllevel", doll.getLevel());
                jsonObject.put("dollframeid", doll.getFrameId());
                jsonObject.put("dollbackground", doll.getBackgroundId());
                jsonObject.put("dollimage", doll.getImage());
                dollJson = jsonObject.toString();

            }else {

                dollJson = "error";

        }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return dollJson;

    }

}
