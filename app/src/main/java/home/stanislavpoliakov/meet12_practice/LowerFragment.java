package home.stanislavpoliakov.meet12_practice;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Класс нижнего фрагмента
 */
public class LowerFragment extends Fragment {
    private static final String TAG = "meet12_logs";
    private EditText lastNameEdit, firstNameEdit;
    private TellActivity mActivity;

    /**
     * В момент присоединения фрагмента (onAttach) стараемся привести вызывающий контекст к
     * интерфейсу TellActivity для взаимодействия Fragment -> Activity
     * @param context вызывающий контекст
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = (TellActivity) context;
        } catch (ClassCastException ex) {
            Log.w(TAG, "Activity must implement TellActivity interface", ex);
        }
    }

    /**
     * Возвращаем в статике экземпляр класса для FragmentManager
     * @return экземпляр класса LowerFragment
     */
    public static LowerFragment newInstance() {
        return new LowerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lower, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initItems(view);
    }

    /**
     * Метод инициализации UI-компонентов
     * @param view, на которой расположены компоненты
     */
    private void initItems(View view) {
        lastNameEdit = view.findViewById(R.id.lastNameEdit);
        firstNameEdit = view.findViewById(R.id.firstNameEdit);

        Button addButton = view.findViewById(R.id.addButton);

        // Собираем данные из полей ввода в Bundle и отправляем в Activity
        addButton.setOnClickListener((v) -> {
            Bundle info = new Bundle();
            info.putString("firstName", firstNameEdit.getText().toString());
            info.putString("lastName", lastNameEdit.getText().toString());

            mActivity.buttonPressed(info);
        });
    }
}

