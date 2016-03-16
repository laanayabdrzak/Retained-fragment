package abderrazak.com.retainedfragement.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private DummyTask mTask;
    private boolean mRunning;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across config changes
        setRetainInstance(true);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancel();
    }
    /**
     * Start the background task.
     */
    public void start() {
        if (!mRunning) {
            mTask = new DummyTask();
            mTask.execute();
            mRunning = true;
        }
    }

    /**
     * Cancel the background task.
     */
    public void cancel() {
        if (mRunning) {
            mTask.cancel(false);
            mTask = null;
            mRunning = false;
        }
    }

    /**
     * Returns the current state of the background task.
     */
    public boolean isRunning() {
        return mRunning;
    }

    /***************************/
    /***** BACKGROUND TASK *****/
    /***************************/
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute();
    }

    private class DummyTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            if (mListener != null) {
                mListener.onPreExecute();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; !isCancelled() && i < 100; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (mListener != null) {
                mListener.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (mListener != null) {
                mListener.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (mListener != null) {
                mListener.onPostExecute();
            }
        }
    }
}
