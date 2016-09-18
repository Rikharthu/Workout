package com.hfad.workout;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.ListView;

// ListFragment содержит только списковое представление
// ListFragmnet, также как и ListActivity регистрируется как слушатель событий своего списка
public class WorkoutListFragment extends ListFragment {
    /* При определении интерфейса формулируются минимальные требования к обьекту для его осмысленного
    взаимодействия с другим обьектом. Это означает, что фрагмент сможет взаимодействовать практикчески
    с любой активностью, при условии, что она реализует необходимый интерфейс */

    // чтобы не привязыватсья к некой активностм, мы создаем интерфейс (так фрагмент будет более reusable)
    static interface WorkoutListListener {
        // так мы сможем сообщить активности, в которой находится наш фрагмент, что на списке фрагменты
        // был произведён щелчок
        void itemClicked(long id);
    };
    // слушатель щелчков (MainActivity)
    private WorkoutListListener listener;

    // при присоеднинении фрагмента к активности
    @Override // в параметры передается активность фрагмента
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // тут мы уже уверены, что активность готова обрабатывать щелчки на списке фрагмента
        // привязывает хозяйскую активность как слушателя щелчков списка
        this.listener = (WorkoutListListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // получить названия упражнений
        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = Workout.workouts[i].getName();
        }

        // Используем адаптер строк
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), // текущий контекст (у фрагмента нету контекста, он использует чужой)
                android.R.layout.simple_list_item_1,
                names);
        // связатеь адаптер с ListView
        setListAdapter(adapter);
        
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // сообщить слушателю о точ, что на одном из вариантов списка был сделан щелчок
        if (listener != null) {
            listener.itemClicked(id);
        }
    }
}
