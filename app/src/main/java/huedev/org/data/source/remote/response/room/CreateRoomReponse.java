package huedev.org.data.source.remote.response.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import huedev.org.data.model.Room;

public class CreateRoomReponse {
    @SerializedName("data")
    @Expose
    public Room room;

}
