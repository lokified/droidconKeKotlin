/*
 * Copyright 2022 DroidconKE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android254.data.repos.mappers

import com.android254.domain.models.Sponsors
import ke.droidcon.kotlin.datasource.local.model.SponsorEntity
import ke.droidcon.kotlin.datasource.remote.sponsors.model.SponsorDTO

fun SponsorDTO.toDomain() = Sponsors(
    sponsorName = name,
    sponsorLogoUrl = logo
)

fun SponsorDTO.toEntity() = SponsorEntity(
    name = name,
    logo = logo,
    tagline = tagline,
    createdAt = createdAt,
    link = link
)
fun SponsorEntity.toDomain() = Sponsors(
    sponsorLogoUrl = logo,
    sponsorName = name
)