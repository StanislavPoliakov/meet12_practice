package home.stanislavpoliakov.meet12_practice;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements TellActivity{
    private static final String TAG = "meet12_logs";
    private SharedPrefInterface mDataInterface;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(SharedPrefService.newIntent(this), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }

    private void initFragments() {
        fragmentManager.beginTransaction()
                .add(R.id.upper, UpperFragment.newInstance(), "upper")
                .add(R.id.lower, LowerFragment.newInstance(), "lower")
                .commit();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mDataInterface = SharedPrefInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            mDataInterface = null;
        }
    };

    @Override
    public void buttonPressed(Bundle info) {
        try {
            mDataInterface.putFirstName(info.getString("firstName"));
            mDataInterface.putLastName(info.getString("lastName"));
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        updateUpper();
    }

    private void updateUpper() {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("firstName", mDataInterface.getFirstName());
            bundle.putString("lastName", mDataInterface.getLastName());
            Log.d(TAG, "updateUpper: Bundle = [" + bundle.getString("firstName") + ", "
                            + bundle.getString("lastName" + "]"));
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }

        TellFragment fragment = (TellFragment) fragmentManager.findFragmentByTag("upper");
        fragment.updateViews(bundle);
    }
}
