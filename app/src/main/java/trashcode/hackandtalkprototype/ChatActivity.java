package trashcode.hackandtalkprototype;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends Activity {

    GestureManager gestureManager;
    ConnectionManager connectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        gestureManager = new GestureManager(this);
        connectionManager = new ConnectionManager(this);
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText messageEditText = findViewById(R.id.messageEditText);
                connectionManager.sendMessage(messageEditText.getText().toString());
                messageEditText.setText("");
            }
        });
    }

    void updateGesture(Gesture gesture) {
        TextView gestureDetectorTextView = findViewById(R.id.gestureDetectorTextView);
        switch (gesture) {
            case WAVE:
                connectionManager.sendMessage(getString(R.string.gesture_wave));
                break;
            case POINT_RIGHT:
                connectionManager.sendMessage(getString(R.string.gesture_point_right));
                break;
            case POINT_LEFT:
                connectionManager.sendMessage(getString(R.string.gesture_point_left));
                break;
        }
    }
}