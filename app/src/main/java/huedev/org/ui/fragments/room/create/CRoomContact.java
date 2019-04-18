package huedev.org.ui.fragments.room.create;

import huedev.org.data.model.Room;
import huedev.org.ui.base.BasePresenter;
import huedev.org.ui.base.BaseView;

public interface CRoomContact {
    interface View extends BaseView {
        void logicCorrect(Room room);
        void logicFaild();
    }

    interface Presenter extends BasePresenter<View> {
        void createRoom(String name, String desc, String status);
    }
}
