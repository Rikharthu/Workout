package com.hfad.workout;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements WorkoutListFragment.WorkoutListListener {
    private static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*=======================================================
        // Получить ссылку на наш фрагмент
        WorkoutDetailFragment frag = (WorkoutDetailFragment)
                            getFragmentManager().findFragmentById(R.id.detail_frag);
        // Вызываем метод фрагмента
        frag.setWorkout(1);
        =======================================================*/
    }

    // Обработчка щелчка на фрагменте (WorkoutListFragment.WorkoutListListener)
    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        // Если есть FrameLayout (он будет только на больших экранах, используюших layout large)
        if (fragmentContainer != null) {
            Log.i(TAG,"FrameLayout exists! Large screen detected. Starting Fragment Transaction");
            WorkoutDetailFragment details = new WorkoutDetailFragment();
            // Транзакция фрагмента - набор изменений, относящихся к фрагменту
            // Получаем обьект FragmentTransaction от диспетчера фрагментов и начинаем транзакцию
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            // передаем фрагменту ИД выбранного упражнения
            details.setWorkout(id);
            // Указываем все действия, которые должны быть сгруппированы в транзакции
            // Замена фрагментя, содержащегося в контейнере (Фрейме)
            // (ид контейнера, фрагмент)
            ft.replace(R.id.fragment_container, details);
            // Добавить транзакцию в BackStack (при нажатии на кнопку 'back' можно будет вернуться к предидущему состоянию фрагмента )
            ft.addToBackStack(null);
            // Определение анимации перехода
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // закрепить изменения
            ft.commit();
        } else {
            Log.i(TAG,"FrameLayout doesn't exist in this layout! Small screen detected. Launching DetailActivity");
            // Если фрейм отсутствует, значит приложение выполяется на устройстве с малым экраном
            // (ведь оно использует простой Layout, где FrameLayout отустствует
            // Т.Е. запускаем активность только если пользователь сидит с телефона
            // (тогда у нас в layout не будет fragmentContainer) !ACHTUNG!
            Intent intent = new Intent(this, DetailActivity.class);
            // передаём ИД упражнения в интент
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int)id);
            startActivity(intent);
        }
    }
}
