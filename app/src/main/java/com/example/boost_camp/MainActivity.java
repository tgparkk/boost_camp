package com.example.boost_camp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boost_camp.model.movie_material.MovieInfo;
import com.example.boost_camp.model.movie_material.movie_material.Items;
import com.example.boost_camp.movie.MovieInfoPresenter;
import com.example.boost_camp.movie.MovieInfoRecyclerAdapter;
import com.example.boost_camp.util.ApiUtils;
import com.example.boost_camp.util.MovieApiInterface;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener
        
        {

    private EditText mMovieNameEditText;
    //mvp 패턴
    //private MovieInfoRepository mRepository;
    //private MovieInfoPresenter mPresenter;

    //검색 요청
    private MovieApiInterface mMovieApiInterface;
    //recyclerView
    private RecyclerView mRecyclerView;
    private MovieInfoRecyclerAdapter mAdapter;
    private ArrayList<Items> mMovieData = new ArrayList<>();

    private LinearLayoutManager manager;

    //무한 스크롤
    private boolean itShouldLoadMore = true;

    private static int now, total,arrSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrofit
        mMovieApiInterface = ApiUtils.getMovieApi();

        //리사이클러뷰
        mRecyclerView = findViewById(R.id.recycler_movie);
        mRecyclerView.setHasFixedSize(false);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        //각 리사이클러뷰 클릭
        mRecyclerView.setAdapter(mAdapter);

        //endless recyclerView
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!mRecyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        if (itShouldLoadMore) {
                            if (now < total) {
                                if (now >= 1001) {
                                    Toast.makeText(MainActivity.this, "1000개 이상입니다...", Toast.LENGTH_SHORT).show();
                                } else {
                                    fetchData();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "더 이상 자료가 없습니다...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        //버튼 클릭 이벤트 구현부
        mMovieNameEditText = findViewById(R.id.movie_string_edit);
        findViewById(R.id.movie_search_button).setOnClickListener(this);
    }

    private void firstSearch(){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mMovieNameEditText.getWindowToken(),0);

        now = 1;
        final String movie_name = mMovieNameEditText.getText().toString();

        if (movie_name.equals("")) {
            Toast.makeText(this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        //
        itShouldLoadMore = false;
        Call<MovieInfo> call = mMovieApiInterface.getMovieItemsMores(movie_name, now);
        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {

                itShouldLoadMore = true;
                total = response.body().getTotal();
                total = (int) Math.ceil(total);

                mMovieData = response.body().getItems();
                if (mMovieData.isEmpty()) {
                    Toast.makeText(MainActivity.this, "'" + movie_name + "'" + " 검색결과는 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                arrSize=10;
                now = 11;
                mAdapter = new MovieInfoRecyclerAdapter(mMovieData);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "통신 오류입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchData() {
        final String movie_name = mMovieNameEditText.getText().toString();

        itShouldLoadMore = false;
        Call<MovieInfo> call = mMovieApiInterface.getMovieItemsMores(movie_name, now);
        //System.out.println("현재 진행 수 " + now);
        //Call<MovieInfo> call = request.getMovieItems(movie_name);
        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {
                //now=now+10;

                itShouldLoadMore = true;

                now = now + 10;
                ArrayList<Items> addInfo = response.body().getItems();
                mAdapter.addItems(addInfo);
                //System.out.println(addInfo.size());
                //mAdapter.notifyDataSetChanged();
                mAdapter.notifyItemRangeInserted(arrSize,10);
                arrSize+=10;
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "통신 오류입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //검색 버튼 클릭
    @Override
    public void onClick(View v) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setMessage("잠시만요..");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    firstSearch();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }).start();
        progressDialog.show();
    }
}