package gr.york.mobiledev2026.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> _counter = new MutableLiveData<>(0);
    LiveData<Integer> counter = _counter;


    public void incrementCounter() {
        int value = _counter.getValue() != null ? _counter.getValue() : 0;
        value++;
        _counter.postValue(value);
    }

    public void decrementCounter() {
        int value = _counter.getValue() != null ? _counter.getValue() : 0;
        value--;
        _counter.postValue(value);
    }
}
