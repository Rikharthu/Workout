package com.hfad.workout;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.FragmentTransaction;

// Фрагмент в Манифесте регистрировать не надо
// Фрагмент создаётся и уничтожается вместе со своей активностью
// ACHTUNG! У каждого фрагмента должен быть определен открытый конструктор без аргументов
// Андроид использует его для повторного создания экземпляра в случае необходимости
public class WorkoutDetailFragment extends Fragment {// Расширяет Fragment
    private static final String TAG="WorkoutDetailFragment";
    // идентификатор комплекса упражнений, выбранного пользователем.
    private long workoutId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Произошёл переворот экрана, фрагмент самовосстанавливает вид
            // надо достаём ид выбранного упражнения
            workoutId = savedInstanceState.getLong("workoutId");
        } else {
            // since we are already in a fragment
            // get CHILD fragment manager
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StopwatchFragment stopwatchFragment = new StopwatchFragment();
            ft.replace(R.id.stopwatch_container, stopwatchFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    // Вызывается тогда, когда Андроид потребуется макет фрагмента
    // ПРОСТО ТАК НАДО
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");

        // LayoutInflater Instantiates a layout XML file into its corresponding View objects.
        // Сообщает Андроид, какой макет используется фрагментом
        // Аналог setContentView() у активностей
        // Аргумент Container передаётся активностью. В нём содержится ViewGroup, в который должен быть
        // вставлен макет фрагмента

        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    // Вызывается перед тем, как фрагмент становитсья видимым (при старте активности)
    public void onStart() {
        // UWAGA! ВСЕГДА ВЫЗЫВАЙТЕ ВЕРСИЮ СУПЕРКЛАССА В РЕАЛИЗАЦИИ ЛЮБЫХ МЕТОДОВ ЖИЗНЕННОГО ЦИКЛА ФРАГМЕНТОВ
        Log.i(TAG,"onStart");
        super.onStart();
        // Получаем корневой обьект View фрагмента
        View view = getView();
        if (view != null) {
            // Используем полученный View фрагмента для получения ссылок на надписи TextView
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            Workout workout = Workout.workouts[(int) workoutId]; title.setText(workout.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }
    }

    @Override // сохранить инфу в бандл перед уничтожение фрагмента
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // сохраняем ИД выбранного упражнения
        savedInstanceState.putLong("workoutId", workoutId);
    }

    // Этот метод используется активностью для передачи информации о выборе пользователя
    public void setWorkout(long id) {
        this.workoutId = id;
    }
}
