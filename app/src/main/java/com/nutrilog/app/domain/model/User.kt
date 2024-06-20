package com.nutrilog.app.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val name: String,
    val email: String,
    val gender: Gender,
    @field:SerializedName("date_of_birth")
    val dateOfBirth: String,
    val token: String,
)

enum class Gender(val label: String) {
    MALE("male"),
    FEMALE("female"),
    OTHER("other"),
}

enum class TeamMember(val data: List<String>) {
    GUNTUR(
        listOf(
            "Fx. Guntur Putra Susanto",
            "Institut Sains Dan Teknologi Akprind",
            "https://www.instagram.com/fx.guntur",
            "https://github.com/fx-guntur",
            "https://www.linkedin.com/in/fx-guntur"
        )
    ),
    NIZAR(
        listOf(
            "Nizar Izzuddin Yatim Fadlan",
            "Institut Sains Dan Teknologi Akprind",
            "https://www.instagram.com/nizariyf_",
            "https://github.com/nizarfadlan",
            "https://www.linkedin.com/in/nizariyf/"
        )
    ),
    GISELLA(
        listOf(
            "Gisella Angel Clarissa",
            "Universitas Katolik Parahyangan",
            "https://www.instagram.com/gisellaangel52/",
            "https://github.com/gisellaa52",
            "https://www.linkedin.com/in/gisellaangel/"
        )
    ),
    RAFI(
        listOf(
            "Rafi Athallah Kurniawan",
            "Universitas Brawijaya",
            "https://www.instagram.com/rafiathallah_",
            "https://github.com/rafthall",
            "https://www.linkedin.com/in/rafi-athallah-kurniawan-b766b82a1/"
        )
    ),
    FAUZAN(
        listOf(
            "Muhammad Fauzan Al-Rafi",
            "Universitas Brawijaya",
            "https://www.instagram.com/fauzanalrafii",
            "https://github.com/fauzanalrafii",
            "https://www.linkedin.com/in/fauzanalrafii/"
        )
    ),
    BAGJA(
        listOf(
            "Bagja Navayo",
            "Universitas Brawijaya",
            "https://www.instagram.com/bagjanavayo",
            "https://github.com/bagjanavayo",
            "https://www.linkedin.com/in/bagjanavayo/"
        )
    ),
    ISRAK(
        listOf(
            "Israk Faradila",
            "Universitas Syiah Kuala",
            "https://www.instagram.com/israk.frdl",
            "https://github.com/Israk20",
            "https://www.linkedin.com/in/israk-faradila-9894471a2/"
        )
    )
}

