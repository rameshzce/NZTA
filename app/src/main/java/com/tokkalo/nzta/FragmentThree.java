package com.tokkalo.nzta;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by rameshkolamala on 05/03/16.
 */
public class FragmentThree extends ListFragment implements AdapterView.OnItemClickListener {

    ImageView fashionImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fashionImg = (ImageView) getView().findViewById(R.id.logo);
        // set a onclick listener for when the button gets clicked
        fashionImg.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Item: ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }


}
