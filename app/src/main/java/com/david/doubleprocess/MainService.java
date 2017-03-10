package com.david.doubleprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by david on 2017/3/10.
 */

public class MainService extends Service {

  private static final String NAME = "MainService";

  private IServiceAidl.Stub binder = new IServiceAidl.Stub() {
    @Override public String getServiceName() throws RemoteException {
      return NAME;
    }
  };

  private ServiceConnection connection = new ServiceConnection() {
    @Override public void onServiceConnected(ComponentName name, IBinder service) {
      String serviceName = "unknown";
      try {
        serviceName = IServiceAidl.Stub.asInterface(service).getServiceName();
      } catch (RemoteException e) {
        e.printStackTrace();
      }
      Toast.makeText(MainService.this, NAME + " bind " + serviceName + " successfully", Toast.LENGTH_SHORT).show();
    }

    @Override public void onServiceDisconnected(ComponentName name) {
      Toast.makeText(MainService.this, NAME + " disconnected SupervisorService", Toast.LENGTH_SHORT).show();
      unbindService(connection);
      Intent intent = new Intent(MainService.this, SupervisorService.class);
      startService(intent);
      bindService(intent, connection, Context.BIND_IMPORTANT);
    }
  };

  @Override public void onCreate() {
    super.onCreate();
    Intent intent = new Intent(MainService.this, SupervisorService.class);
    startService(intent);
    bindService(intent, connection, Context.BIND_IMPORTANT);
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    unbindService(connection);
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return binder;
  }
}
