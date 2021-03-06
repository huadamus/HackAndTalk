package trashcode.hackandtalkprototype;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

final class ConnectionManager {

    private ChatActivity chatActivity;
    private WebSocket ws;

    ConnectionManager(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.8.158:5678/chat").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    void login(String name) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            ws.send(json.toString());
        }
        catch(JSONException je) {
            je.printStackTrace();
        }
    }

    void sendMessage(String message) {
        ws.send(message);
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            try {
                JSONObject message = new JSONObject(text);
                chatActivity.addMessage(message.getString("sender") + ": " + message.getString("userMessage"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {

        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {

        }
    }
}