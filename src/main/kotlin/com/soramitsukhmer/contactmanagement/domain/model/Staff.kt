package com.soramitsukhmer.contactmanagement.domain.model

import com.soramitsukhmer.contactmanagement.api.request.CompanyDTO
import com.soramitsukhmer.contactmanagement.api.request.RequestStaffDTO
import com.soramitsukhmer.contactmanagement.api.request.StaffDTO
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "staffs")
data class Staff(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id : Long = 0,
        @Column(name = "name")
        var name: String,
        @Column(name = "gender")
        var gender: String,
        @Column(name = "location")
        var location: String?,
        @Column(name = "position")
        var position: String?,
        @CreationTimestamp
        @Column(name = "created_at")
        var createdAt: LocalDateTime = LocalDateTime.now(),
        @UpdateTimestamp
        @Column(name = "updated_at")
        var updatedAt: LocalDateTime = LocalDateTime.now()
){
    @ManyToOne
    @JoinColumn(name = "company_id")
    lateinit var company: Company

    fun toDTO() = StaffDTO(
            id = id,
            name = name,
            position = position,
            company = company.toDTO(),
            gender = gender,
            location = location,
            createdAt = createdAt,
            updatedAt = updatedAt
    )

    fun updateStaff(reqStaffDTO: RequestStaffDTO, company: Company) : Staff{
        return this.apply {
            name = reqStaffDTO.name
            position = reqStaffDTO.position
            gender = reqStaffDTO.gender
            location = reqStaffDTO.location
            this.company = company
        }
    }

    companion object{
        fun fromReqDTO(reqStaffDTO: RequestStaffDTO, company: Company) = Staff(
                name = reqStaffDTO.name,
                position = reqStaffDTO.position,
                gender = reqStaffDTO.gender,
                location = reqStaffDTO.location
        ).apply { this.company = company }
    }
}