package com.example.uicomponents

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PosterImage(url: String, description: String, modifier: Modifier = Modifier) {

    GlideImage(
        model = url,
        contentDescription = description,
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size)),
        loading = placeholder(R.drawable.placeholder),
        failure = placeholder(R.drawable.placeholder)
    )
}
