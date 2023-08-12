package inc.kaizen.example.pagination.database

import inc.kaizen.example.pagination.client.Beer

fun Beer.toEntity(): BeerEntity {
    return BeerEntity(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        firstBrewed = first_brewed,
        imageUrl = image_url
    )
}

fun BeerEntity.toBeer(): Beer {
    return Beer(
        id = id,
        name = name,
        tagline = tagline,
        description = description,
        first_brewed = firstBrewed,
        image_url = imageUrl
    )
}