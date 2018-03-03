package com.qianfeng.day26_baidumap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.LoginFilter;
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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
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

    private MapView mMapView = null;
    private SearchView mPoiSv = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;
    private boolean isFirstLoc = true;
    private LatLng mCurrentLocation;
    private String mCity;
    private GeoCoder mGeoCoder;
    private PoiSearch mPoiSearch;
    private RadioGroup mTypeRg;
    private EditText mDestEt;
    private Button mGoBtn;
    private RoutePlanSearch mRoutePlanSearch;
    private RadioButton mWalkRb;
    private RadioButton mBusRb;
    private RadioButton mDriveRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        initViews();
        initLocation();
        initOverlay();
        initPoiSearch();
        initRoutePlanSearch();
    }

    //初始化路径规划功能
    private void initRoutePlanSearch() {
        //获得RoutePlanSearch对象
        mRoutePlanSearch = RoutePlanSearch.newInstance();
        //定义获得结果的监听
        mRoutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if(walkingRouteResult == null ||
                        walkingRouteResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                    //创建步行路径的覆盖物
                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                    //设置点击事件监听
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    //设置路线
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //添加到地图上
                    overlay.zoomToSpan();
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                if(transitRouteResult == null ||
                        transitRouteResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                    //创建步行路径的覆盖物
                    TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
                    //设置点击事件监听
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    //设置路线
                    overlay.setData(transitRouteResult.getRouteLines().get(0));
                    //添加到地图上
                    overlay.zoomToSpan();
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if(drivingRouteResult == null ||
                        drivingRouteResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR){
                    //创建步行路径的覆盖物
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    //设置点击事件监听
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    //设置路线
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    //添加到地图上
                    overlay.zoomToSpan();
                    overlay.addToMap();
                }
            }
        });
        //进行不同类型的搜索
        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWalkRb.isChecked()){
                    //搜索步行的结果
                    mRoutePlanSearch.walkingSearch(new WalkingRoutePlanOption()
                    .from(PlanNode.withLocation(mCurrentLocation)) //出发点
                    .to(PlanNode.withCityNameAndPlaceName(mCity,mDestEt.getText().toString()) //目的地
                    ));
                }else if(mDriveRb.isChecked()){
                    //搜索开车结果
                    mRoutePlanSearch.drivingSearch(new DrivingRoutePlanOption()
                            .from(PlanNode.withLocation(mCurrentLocation)) //出发点
                            .to(PlanNode.withCityNameAndPlaceName(mCity,mDestEt.getText().toString()) //目的地
                            ));
                }else{
                    //搜索乘车结果
                    mRoutePlanSearch.transitSearch(new TransitRoutePlanOption()
                            .city(mCity)
                            .from(PlanNode.withLocation(mCurrentLocation)) //出发点
                            .to(PlanNode.withCityNameAndPlaceName(mCity,mDestEt.getText().toString()) //目的地
                            ));
                }
            }
        });
    }

    /**
     * 兴趣点的覆盖物
     */
    class MyPoiOverlay extends PoiOverlay{

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        //实现POI的点击事件
        @Override
        public boolean onPoiClick(int i) {
            //获得当前点击的POI
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            String info = poiInfo.name +"--"+ poiInfo.address;
            Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
            Log.i("xxx", "onPoiClick: "+info);
            return true;
        }
    }

    //初始化搜索功能
    private void initPoiSearch() {
        //获得PoiSearch对象
        mPoiSearch = PoiSearch.newInstance();
        //定义获得结果的监听
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                Log.i("xxx", "PoiResult: "+poiResult);
                if(poiResult == null ||
                        poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(poiResult.error == SearchResult.ERRORNO.NO_ERROR){
                    //把地图的覆盖物清空
                    mBaiduMap.clear();
                    //创建覆盖物
                    PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                    //设置搜索结果
                    overlay.setData(poiResult);
                    //添加到地图上
                    overlay.addToMap();
                    //进行缩放
                    overlay.zoomToSpan();
                    //设置点击覆盖物的监听
                    mBaiduMap.setOnMarkerClickListener(overlay);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
        //实现进行搜索的监听
        mPoiSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //按城市进行搜索
//                mPoiSearch.searchInCity(new PoiCitySearchOption()
//                    .city(mCity)
//                    .keyword(query)
//                    .pageNum(0)
//                    .pageCapacity(10));
                //按半径进行搜索
                mPoiSearch.searchNearby(new PoiNearbySearchOption()
                        .location(mCurrentLocation)
                        .radius(10000) //单位是米
                        .keyword(query)
                        .pageNum(0)
                        .pageCapacity(10)
                );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //初始化GeoCoder，将经纬度转换为地理位置
    private void initGeoCoder(LatLng ll) {
        //获取GeoCoder对象
        mGeoCoder = GeoCoder.newInstance();
        //进行反向编码
        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
        //设置获得编码结果的监听
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                //正向编码结果 地址——》经纬度
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                //反向编码结果 经纬度——》地址
                Log.i("xxx",result.getAddress());
                Toast.makeText(MainActivity.this, result.getAddress(), Toast.LENGTH_SHORT).show();
                mCity = result.getAddressDetail().city;
            }
        });
    }

    //初始化覆盖物
    private void initOverlay() {
        //定义图标
        BitmapDescriptor ooA = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        BitmapDescriptor ooB = BitmapDescriptorFactory.fromResource(R.mipmap.icon_markb);
        BitmapDescriptor ooC = BitmapDescriptorFactory.fromResource(R.mipmap.icon_markc);
        //定义经纬度
        LatLng llA = new LatLng(30.481594,114.412826);
        LatLng llB = new LatLng(30.483594,114.414826);
        LatLng llC = new LatLng(30.485594,114.416826);
        //创建覆盖物的参数
        OverlayOptions opA = new MarkerOptions().position(llA).icon(ooA).zIndex(5).draggable(true);
        OverlayOptions opB = new MarkerOptions().position(llB).icon(ooB).zIndex(5).draggable(true);
        OverlayOptions opC = new MarkerOptions().position(llC).icon(ooC).zIndex(5).draggable(true);
        //添加覆盖物
        final Marker mkA = (Marker) mBaiduMap.addOverlay(opA);
        final Marker mkB = (Marker) mBaiduMap.addOverlay(opB);
        final Marker mkC = (Marker) mBaiduMap.addOverlay(opC);
        //添加点击覆盖物的监听
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Button btn = new Button(MainActivity.this);
                if(marker == mkA){
                    btn.setText("点击了A");
                }else if(marker == mkB){
                    btn.setText("点击了B");
                }else if(marker == mkC){
                    btn.setText("点击了C");
                }
                //创建一个弹出窗体 -40代表窗体向上移动的距离
                InfoWindow window = new InfoWindow(btn,marker.getPosition(),-40);
                //在地图上显示弹出窗
                mBaiduMap.showInfoWindow(window);
                return true;
            }
        });
    }

    //初始化定位功能
    private void initLocation() {
        //获得BaiduMap对象
        mBaiduMap = mMapView.getMap();
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //创建定位客户端
        mLocationClient = new LocationClient(this);
        //设置定位的选项
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);//扫描间隔 1000ms
        mLocationClient.setLocOption(option);
        //开始定位
        mLocationClient.start();
        //设置定位的监听
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation != null){
                    //创建定位数据
                    MyLocationData data = new MyLocationData.Builder()
                            .accuracy(bdLocation.getRadius())
                            .direction(bdLocation.getDirection())
                            .latitude(bdLocation.getLatitude())
                            .longitude(bdLocation.getLongitude())
                            .build();
                    //给地图设置定位数据
                    mBaiduMap.setMyLocationData(data);
                    //第一次定位时，移动到当前位置
                    if(isFirstLoc){
                        isFirstLoc = false;
                        LatLng ll = new LatLng(bdLocation.getLatitude(),
                                bdLocation.getLongitude());
                        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                        //移动地图
                        mBaiduMap.animateMapStatus(update);
                        //保存当前位置
                        mCurrentLocation = ll;
                        //进行反向编码
                        initGeoCoder(ll);
                    }
                }
            }
        });
    }

    private void initViews() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mPoiSv = (SearchView) findViewById(R.id.poi_sv);
        mTypeRg = (RadioGroup)findViewById(R.id.type_rg);
        mDestEt = (EditText)findViewById(R.id.dest_et);
        mGoBtn = (Button)findViewById(R.id.go_btn);
        mWalkRb = (RadioButton)findViewById(R.id.walk_rb);
        mBusRb = (RadioButton)findViewById(R.id.bus_rb);
        mDriveRb = (RadioButton)findViewById(R.id.drive_rb);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
