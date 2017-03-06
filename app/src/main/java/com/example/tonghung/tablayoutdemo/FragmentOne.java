package com.example.tonghung.tablayoutdemo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tonghung.tablayoutdemo.database.DatabaseHelper;
import com.example.tonghung.tablayoutdemo.object.Category;
import com.example.tonghung.tablayoutdemo.object.Product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class FragmentOne extends Fragment {
    private EditText etNamePro, etPrice, etDay;
    private Spinner spinCate;
    private ImageView ivGallery;
    private Button btnAdd;
    public final static String LB = "LB";
    private DatePickerDialog datePicker;
    private Calendar calendar;
    private Date dayHolder;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatabaseHelper db;
    private List<Category> listCate;
    private Category currentCate;
    private Bitmap currentImage;


    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        etNamePro = (EditText) rootView.findViewById(R.id.editTextNamePro);
        etPrice = (EditText) rootView.findViewById(R.id.editTextPrice);
        etDay = (EditText) rootView.findViewById(R.id.editTextDate);
        spinCate = (Spinner) rootView.findViewById(R.id.spinnerCate);
        ivGallery = (ImageView) rootView.findViewById(R.id.imageViewGallery);
        btnAdd = (Button) rootView.findViewById(R.id.buttonAddPro);

        calendar = Calendar.getInstance();
        etDay.setText(dateFormat.format(calendar.getTime()));
        dayHolder = calendar.getTime();
        db = new DatabaseHelper(getActivity());

        initDatePicker();
        loadSpinnerCate();
        choosePicture();
        getCateID();
        insertProduct();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "Welcome to Fragment one", Toast.LENGTH_SHORT).show();
    }

    private void initDatePicker(){
        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month, dayOfMonth);
                        dayHolder = calendar.getTime();
                        etDay.setText(dateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                        (Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
    }

    private void loadSpinnerCate(){
        Cursor c = db.selectCate();
        listCate = new ArrayList<>();
        while(c.moveToNext()){
            listCate.add(new Category(c.getInt(0), c.getString(1)));
        }
        spinCate.setAdapter(new ArrayAdapter(getContext(), android.R.layout
                .simple_spinner_dropdown_item, listCate));
    }

    private void insertProduct(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product pro = new Product();
                pro.setName(etNamePro.getText().toString());
                pro.setCate(currentCate.getId());
                pro.setPrice(Integer.parseInt(etPrice.getText().toString()));
                pro.setImage(currentImage);
                pro.setDate(dayHolder);

                if(db.insertProduct(pro) != -1){
                    Toast.makeText(getContext(), "Insert product successful", Toast.LENGTH_SHORT)
                            .show();
                    clearForm();
                }else{
                    Toast.makeText(getContext(), "Insert product failure", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void getCateID(){
        spinCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCate = (Category) spinCate.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void clearForm(){
        etNamePro.setText(null);
        etPrice.setText(null);
    }







    private void choosePicture(){
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Chooose Picture"), 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                currentImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                        uri);
                ivGallery.setImageBitmap(currentImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
