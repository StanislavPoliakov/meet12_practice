package home.stanislavpoliakov.meet12_practice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.Call;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class SharedPrefService extends Service {
    private static final String TAG = "meet12_logs";
    private final String PREF = "CREDENTIALS";
    private SharedPreferences sharedPreferences;
    private ExecutorService pool = Executors.newSingleThreadExecutor();

    public static Intent newIntent(Context context) {
        return new Intent(context, SharedPrefService.class);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new SharedPrefInterface.Stub() {

            @Override
            public String getFirstName() throws RemoteException {
                Callable<String> getFirst = () -> {
                    sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE);
                    Log.d(TAG, "getFirstName: Thread = " + sharedPreferences.getString("firstName", "Значение в базе отсутствует"));
                    return sharedPreferences.getString("firstName", "Значение в базе отсутствует");
                };
                Future<String> future = pool.submit(getFirst);
                try {
                    return future.get();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                }
                    return null;
                }


            @Override
            public String getLastName() throws RemoteException {
                Callable<String> getLast = () -> {
                    sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE);
                    Log.d(TAG, "getLastName: Thread = " + sharedPreferences.getString("lastName", "Значение в базе отсутствует"));
                    return sharedPreferences.getString("lastName", "Значение в базе отсутствует");
                };
                Future<String> future = pool.submit(getLast);
                try {
                    return future.get();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            public void putFirstName(String firstName) throws RemoteException {
                Runnable putR = () -> {
                    sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("firstName", firstName);
                    Log.d(TAG, "putFirstName: Thread = " + firstName);
                    editor.apply();
                };
                pool.submit(putR);
            }

            @Override
            public void putLastName(String lastName) throws RemoteException {
                Runnable runR = () -> {
                    sharedPreferences = getSharedPreferences(PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastName", lastName);
                    Log.d(TAG, "putLastName: Thread = " + lastName);
                    editor.apply();
                };
                pool.submit(runR);
            }
        };
    }

    @Override
    public boolean onUnbind(Intent intent) {
        pool.shutdown();
        return super.onUnbind(intent);
    }
}
