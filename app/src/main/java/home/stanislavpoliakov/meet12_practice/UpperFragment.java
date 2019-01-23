package home.stanislavpoliakov.meet12_practice;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpperFragment extends Fragment implements TellFragment{
    private TextView fullName;

    public static UpperFragment newInstance() {
        return new UpperFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upper, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems(view);
    }

    private void initItems(View view) {
        fullName = view.findViewById(R.id.fullNameTextView);
    }

    @Override
    public void updateViews(Bundle info) {
        StringBuilder builder = new StringBuilder();
        builder.append(info.getString("firstName"));
        builder.append(" ");
        builder.append(info.getString("lastName"));
        fullName.setText(builder.toString());
    }
}
