package shadattonmoy.sqlite;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shadat Tonmoy on 6/18/2017.
 */

public class Message {
    public Message(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
