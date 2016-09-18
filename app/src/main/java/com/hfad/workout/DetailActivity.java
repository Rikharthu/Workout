package com.hfad.workout;

import android.app.Activity;
import android.os.Bundle;

public class DetailActivity extends Activity {
    public static final String EXTRA_WORKOUT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // получаем ссылку на фрагмент
        WorkoutDetailFragment workoutDetailFragment = (WorkoutDetailFragment)
            getFragmentManager().findFragmentById(R.id.detail_frag);
        // достаём ИД упражнения из интента и передаем фрагменту
        int workoutId = (int) getIntent().getExtras().get(EXTRA_WORKOUT_ID);
        workoutDetailFragment.setWorkout(workoutId);
    }
}
