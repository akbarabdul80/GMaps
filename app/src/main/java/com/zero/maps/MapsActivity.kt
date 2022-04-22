package com.zero.maps

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.zero.maps.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map_normal -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.map_satellite -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.map_hybrid -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            R.id.map_terrain -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Jogja and move the camera
        val jogja = LatLng(-7.797068, 110.370529)
        mMap.addMarker(MarkerOptions().position(jogja).title("Jogja"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jogja, 10f))
        onMapLongClicked((googleMap))
        onPoiClicked(googleMap)
    }

    private fun onMapLongClicked(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            val snippet = "Lat : " + latLng.longitude + " long : " + latLng.longitude
            map.addMarker(MarkerOptions().position(latLng).title("Dropped pin").snippet(snippet))
        }
    }

    private fun onPoiClicked(map: GoogleMap) {
        map.setOnPoiClickListener { pointOfInterest ->
            val marker = map.addMarker(
                MarkerOptions().position(pointOfInterest.latLng).title(pointOfInterest.name)
            )
            marker!!.showInfoWindow()
        }
    }
}