## Retained fragment

The sample code above serves as a basic example of how to retain an AsyncTask across a configuration change using retained Fragments. The code guarantees that progress updates and results are delivered back to the currently displayed Activity instance and ensures that we never accidentally leak an AsyncTask during a configuration change. The design consists of two classes, a MainActivity
