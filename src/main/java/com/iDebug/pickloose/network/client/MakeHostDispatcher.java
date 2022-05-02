package com.iDebug.pickloose.network.client;

import com.iDebug.pickloose.UserManager;
import com.iDebug.pickloose.network.FEEDBACK;
import com.iDebug.pickloose.network.Response;

class MakeHostDispatcher extends Dispatcher {
    @Override
    void dispatch(Response response) {
        try {
            if(response.getStatus() == FEEDBACK.SUCCEED) {
                String userid = UserManager.getInstance().getMyUser().getUserid();
                UserManager.getInstance().setRole(userid,"HOST");
            }
            else {
                System.out.println("Host Request Rejected!!!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
