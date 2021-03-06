package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.abbisqq.freefall.MainActivity;
import com.abbisqq.freefall.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StartingFragment.OnStartingFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StartingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartingFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton newGameButton;
    MainActivity mainActivity;
    TextView highscoreView;
    MediaPlayer mediaPlayer,buttonSound;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private OnStartingFragmentInteractionListener mListener;

    public StartingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartingFragment newInstance(String param1, String param2) {
        StartingFragment fragment = new StartingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_starting, container, false);
        newGameButton = (ImageButton) view.findViewById(R.id.newGameButton);
        highscoreView = (TextView)view.findViewById(R.id.highscoreView);
        newGameButton.setOnClickListener(this);
        sharedPreferences = getActivity().getSharedPreferences("h",0);
        highscoreView.setText("Highscore:"+String.valueOf(sharedPreferences.getInt("h",0)));
        mediaPlayer = MediaPlayer.create(getActivity(),R.raw.startingmusic);
        mediaPlayer.start();
        buttonSound = MediaPlayer.create(getActivity(),R.raw.press);
        return view;
    }


    @Override
    public void onClick(View view) {
        mediaPlayer.stop();
        buttonSound.start();
         mainActivity = (MainActivity)getActivity();
         if(view==newGameButton){
             mainActivity.loadGameFragment();
         }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnStartingFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartingFragmentInteractionListener) {
            mListener = (OnStartingFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStartingFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnStartingFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mediaPlayer.start();
    }
}
