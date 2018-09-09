package myevents.almansa.unir.es.myevents.view.interfaces

import myevents.almansa.unir.es.myevents.model.Img

interface EventImagesView: LoadingContentView {
        fun updateRecyclerView(images: List<Img>)
}