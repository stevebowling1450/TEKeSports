package com.teky.tekesports.Utils;

import com.teky.tekesports.Model.Message;
import com.teky.tekesports.Model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.teky.tekesports.Components.Constants.PLAYER_ADMIN;
import static com.teky.tekesports.Components.Constants.PLAYER_SCRUMMASTER;

/**
 * Created by lennyhicks on 12/1/16.
 * toornament-android.
 */

public class Utils {

    public static String formattedDate() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sf.format(date);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM" + "-01", Locale.getDefault());
        return sf.format(date);
    }

    public static String chatDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sf.format(date);
    }

    public static String formatChat(Message message) {
        User messageSender = message.getSender();
        if (message.getMessage() != null) {
            if (message.getMessage().startsWith("::") && messageSender.getUserRights() >= PLAYER_ADMIN) {
                if (message.getMessage().startsWith("::announce")) {
                    return message.getMessage().replace("::announce", "[ANNOUNCEMENT] ");
                } else if (message.getMessage().startsWith("::notice")) {
                    return message.getMessage().replace("::notice", "[NOTICE] ");
                } else {
                    //Invalid Command
                }
            } else if (messageSender.getUserRights() >= PLAYER_SCRUMMASTER) {
                return getRank(messageSender) + messageSender.getUsername() + " : " + message.getMessage();
            } else if (messageSender.getUserRights() < PLAYER_SCRUMMASTER) {
                return messageSender.getUsername() + " : " +message.getMessage();
            }
        }
        return null;
    }

    public static String getRank(User user) {
        switch (user.getUserRights()) {
            case 2:
                return "[ScrumMaster] ";
            case 3:
                return "[Moderator] ";
            case 4:
                return "[Admin] ";
            case 5:
                return "[Owner] ";
            default:
                return "[HACKER] ";
        }
    }
}
