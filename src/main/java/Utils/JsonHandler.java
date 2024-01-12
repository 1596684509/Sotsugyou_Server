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

            System.out.println(jsonObject.toString());

        } catch (JSONException e) {
            return null;
        }

        return user;

    }

    public static Doll jsonToDoll(JSONObject jsonObject) {

        Doll doll = new Doll();

        try {

            doll.setName(jsonObject.getString("dollname"));
            doll.setExp(jsonObject.getInt("exp"));
            doll.setLevel(jsonObject.getInt("level"));
            doll.setFrameId(jsonObject.getInt("dollframeid"));
            doll.setBackgroundId(jsonObject.getInt("dollbackgroundid"));
            doll.setImage(jsonObject.getString("dollimage"));

            System.out.println(jsonObject.toString());

        } catch (JSONException e) {
            return null;
        }

        return doll;

    }

}
