package com.triangle.n12medic.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.triangle.n12medic.model.News

@Composable
fun NewsComponent(
    modifier: Modifier = Modifier,
    news: News
) {
    Card(
        modifier = modifier
            .height(152.dp)
            .width(270.dp)
            .padding(PaddingValues(end = 10.dp)),
        elevation = 0.dp,
        backgroundColor = Color(0xFF76B3FF)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            GlideImage(
                imageModel = news.image,
                modifier = Modifier
                    .fillMaxHeight(0.85f)
                    .align(Alignment.BottomEnd),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillHeight,
                    alignment = Alignment.BottomEnd
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight()
                    .padding(PaddingValues(start = 16.dp, bottom = 16.dp, top = 16.dp)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = news.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
                Column {
                    Text(
                        text = news.description,
                        fontSize = 10.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${news.price} ₽",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }
        }
    }
}