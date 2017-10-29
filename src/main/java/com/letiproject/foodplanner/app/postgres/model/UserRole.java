package com.letiproject.foodplanner.app.postgres.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_role")
@AssociationOverrides({
        @AssociationOverride(name = "pk.user",
                joinColumns = @JoinColumn(name = "id_user")),
        @AssociationOverride(name = "pk.role",
                joinColumns = @JoinColumn(name = "id_role"))})
public class UserRole {

    private UserRoleId pk = new UserRoleId();

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "name")
    private String name;

    public UserRole() {
        super();
    }

    public UserRole(final String name) {
        super();
        this.name = name;
    }

    //

    @EmbeddedId
    public UserRoleId getPk() {
        return pk;
    }

    public void setPk(UserRoleId pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(User stock) {
        getPk().setUser(stock);
    }

    @Transient
    public Role getRole() {
        return getPk().getRole();
    }

    public void setRole(Role category) {
        getPk().setRole(category);
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role role = (Role) obj;
        if (!role.equals(role.getRole())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Role [name=").append(name).append("]").append("[id=").append(pk).append("]");
        return builder.toString();
    }


    @Embeddable
    public class UserRoleId implements java.io.Serializable {

        private User user;
        private Role role;

        @ManyToOne
//        @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @ManyToOne
//        @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)
        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserRoleId that = (UserRoleId) o;

            if (user != null ? !user.equals(that.user) : that.user != null) return false;
            if (role != null ? !role.equals(that.role) : that.role != null)
                return false;

            return true;
        }

        public int hashCode() {
            int result;
            result = (user != null ? user.hashCode() : 0);
            result = 31 * result + (role != null ? role.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "UserRoleId{" +
                    "user=" + user.getId() +
                    ", role=" + role.getId() +
                    '}';
        }
    }
}