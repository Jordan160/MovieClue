package com.jvetter2.movieclue;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_settings, null);
        RadioGroup rGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        RadioButton stop = v.findViewById(R.id.stopMusicButton);
        RadioButton play = v.findViewById(R.id.playMusicButton);

        if (MainActivity.mediaPlayer.isPlaying()) {
            stop.setChecked(false);
            play.setChecked(true);
        } else {
            stop.setChecked(true);
            play.setChecked(false);
        }

        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor preferenceEdit = preferences.edit();

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if (checkedRadioButton.getText().equals(getString(R.string.musicOff))) {
                    MainActivity.mediaPlayer.stop();
                    preferenceEdit.putBoolean("musicPlaying", false);
                    preferenceEdit.commit();
                } else if (!MainActivity.mediaPlayer.isPlaying()) {
                    MainActivity.mediaPlayer.start();
                    preferenceEdit.putBoolean("musicPlaying", true);
                    preferenceEdit.commit();
                }
            }
        });
        return v;
    }

}