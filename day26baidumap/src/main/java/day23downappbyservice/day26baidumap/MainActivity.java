package day23downappbyservice.day26baidumap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class MainActivity extends AppCompatActivity {

    private MapView mBaiduMap;
    private BaiduMap mBaidu;
    private LocationClient mLoactionClient;
    private boolean isFirst  = true;
    private GeoCoder mGeo;
    private String mLoactionCity;
    private SearchView mSearchView;
    private LatLng mCurrentLl;
    private PoiSearch mPoiSearch;
    private RoutePlanSearch mRoutePlan;
    private RadioGroup mStyleRg;
    private EditText mInput;
    private Button mWalkBtn;
    private RadioButton mBusBtn;
    private RadioButton mRunkBtn;
    private RadioButton mDriveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initViews();
        initMarks();
        initPoiSearch();
        initRouteSearch();
    }

    private void initRouteSearch() {
        mRoutePlan = RoutePlanSearch.newInstance();
        mStyleRg = (RadioGroup)findViewById(R.id.style_rg);
        mInput = (EditText)findViewById(R.id.go_to_et);
        mWalkBtn = (Button)findViewById(R.id.go_to_btn);
        mBusBtn = (RadioButton)findViewById(R.id.bus);
        mRunkBtn = (RadioButton)findViewById(R.id.walk);
        mDriveBtn = (RadioButton)findViewById(R.id.drive);
        mWalkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRunkBtn.isChecked()){
                    mRoutePlan.walkingSearch(new WalkingRoutePlanOption()
                            .from(PlanNode.withLocation(mCurrentLl))
                            .to(PlanNode.withCityNameAndPlaceName(mLoactionCity,mInput.getText().toString())));
                }else if (mBusBtn.isChecked()){
                    mRoutePlan.transitSearch(new TransitRoutePlanOption().city(mLoactionCity).from(PlanNode.withLocation(mCurrentLl))
                            .to(PlanNode.withCityNameAndPlaceName(mLoactionCity,mInput.getText().toString())));
                }else if(mDriveBtn.isChecked()){
                    mRoutePlan.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(mCurrentLl)).to(PlanNode.withCityNameAndPlaceName(mLoactionCity,mInput.getText().toString())));
                }
            }
        });
       mRoutePlan.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
           @Override
           public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
               if(walkingRouteResult==null||walkingRouteResult.error==SearchResult.ERRORNO.RESULT_NOT_FOUND){
                   Toast.makeText(MainActivity.this,"没有找到想要的结果",Toast.LENGTH_SHORT).show();
               }else if(walkingRouteResult.error==SearchResult.ERRORNO.NO_ERROR){
                   WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaidu);
                   mBaidu.setOnMarkerClickListener(overlay);
                   overlay.setData(walkingRouteResult.getRouteLines().get(0));
                   overlay.zoomToSpan();
                   overlay.addToMap();
               }
           }

           @Override
           public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

           }

           @Override
           public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

           }
       });
    }

    private void initPoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        //搜索监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPoiSearch.searchNearby(new PoiNearbySearchOption().location(mCurrentLl)
                .radius(1000).keyword(query).pageNum(0).pageCapacity(10));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //搜索结果的监听
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {


            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if(poiResult==null||poiResult.error== SearchResult.ERRORNO.RESULT_NOT_FOUND){
                    Toast.makeText(MainActivity.this, "没有找到搜索结果", Toast.LENGTH_SHORT).show();
                }else if(poiResult.error ==SearchResult.ERRORNO.NO_ERROR){
                    mBaidu.clear();
                    PoiOverlay poiOverlay = new MyPoiOverlay(mBaidu);
                    poiOverlay.setData(poiResult);
                    poiOverlay.zoomToSpan();
                    poiOverlay.addToMap();
                    mBaidu.setOnMarkerClickListener(poiOverlay);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }


        });
    }

    class  MyPoiOverlay extends PoiOverlay{

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            String str = poiInfo.name+"---"+poiInfo.address;
            Log.e("111", "onPoiClick: "+str );
            return true;
        }
    }
    private void initGeoCode(LatLng ll){
        mGeo = GeoCoder.newInstance();
        mGeo.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
        mGeo.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                Log.e("111", "onGetReverseGeoCodeResult: "+result.getAddress() );
                Log.e("111", "onGetReverseGeoCodeResult: "+result.getAddressDetail() );
                mLoactionCity = result.getAddressDetail().city;
            }
        });
    }

    private void initMarks() {
        BitmapDescriptor mark1 = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        LatLng ll = new LatLng(30.482595,114.412826);
        OverlayOptions options = new MarkerOptions().position(ll).icon(mark1).zIndex(5).draggable(true);
        final Marker mka = (Marker) mBaidu.addOverlay(options);
        BitmapDescriptor mark2 = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        LatLng ll2 = new LatLng(30.483595,114.412826);
        OverlayOptions options2 = new MarkerOptions().position(ll2).icon(mark2).zIndex(5).draggable(true);
        final Marker mkb = (Marker) mBaidu.addOverlay(options2);
        BitmapDescriptor mark3 = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        LatLng ll3 = new LatLng(30.484595,114.412826);
        OverlayOptions options3 = new MarkerOptions().position(ll3).icon(mark3).zIndex(5).draggable(true);
        final Marker mkc = (Marker) mBaidu.addOverlay(options3);
        mBaidu.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Button btn = new Button(MainActivity.this);
                if(marker == mka){
                    btn.setText("点击了A");
                }else if(marker == mkb){
                    btn.setText("点击了B");
                }else if(marker == mkc){
                    btn.setText("点击了C");
                }
                InfoWindow info = new InfoWindow(btn,marker.getPosition(),0);
                mBaidu.showInfoWindow(info);
                return true;
            }
        });
    }

    private void initViews() {
        mSearchView = (SearchView) findViewById(R.id.location_sv);
        mBaiduMap = (MapView) findViewById(R.id.bmapView);
        mBaidu = mBaiduMap.getMap();
        mBaidu.setMyLocationEnabled(true);
        mLoactionClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLoactionClient.setLocOption(option);
        mLoactionClient.start();
        mLoactionClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation!=null){
                    MyLocationData data = new MyLocationData.Builder()
                            .accuracy(bdLocation.getRadius())
                            .direction(bdLocation.getDirection())
                            .latitude(30.481594)
                            .longitude(114.412826)
                            .build();
                    mBaidu.setMyLocationData(data);
                    if(isFirst){
                        isFirst = false;
                        LatLng ll = new LatLng(30.481594,114.412826);
                        initGeoCode(ll);
                        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                        mCurrentLl = ll;
                        mBaidu.animateMapStatus(update);
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBaiduMap.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBaiduMap.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.onDestroy();

    }
}
