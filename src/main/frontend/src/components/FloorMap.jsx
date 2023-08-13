import React from "react";
import { MapContainer, ImageOverlay, Marker, Popup } from "react-leaflet";

const FloorMap = ({ floorImage, places, onMarkerClick }) => {
  return (
    <div className="floor-map">
      <MapContainer
        center={[0, 0]} // Domyślny punkt centralny
        zoom={2} // Domyślny poziom przybliżenia
        style={{ height: "1000px", width: "10%" }}
        dragging={false}
        scrollWheelZoom={false}
      >
        <ImageOverlay
          url={floorImage}
          bounds={[
            // Współrzędne graniczne obrazu (dolny lewy i górny prawy róg)
            [0, 0],
            [1000, 500], // Zakładam wymiary 1000x1000, dostosuj do swojego obrazu
          ]}
        />
        {places.map((place) => (
          <Marker
            key={place.id}
            position={[place.lat, place.lng]}
            onClick={() => onMarkerClick(place.id)}
          >
            <Popup>{place.name}</Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
};

export default FloorMap;
