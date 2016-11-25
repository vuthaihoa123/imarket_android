package com.example.framgia.imarketandroid.data.model;

import java.lang.reflect.Field;
import java.util.jar.Attributes;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by framgia on 21/09/2016.
 */
public class Migration implements RealmMigration {
    private final int MIGRATION_VERSION_0 = 0;
    private final int MIGRATION_VERSION_1 = 1;
    private final int MIGRATION_VERSION_2 = 2;
    private final int MIGRATION_VERSION_3 = 3;

    private final String CATEGORY_TABLE_NAME = "Category";
    private final String COMMERCE_TABLE_NAME = "CommerceCanter";
    private final String FIELD_ID = "mId";
    private final String FIELD_NAME = "mName";
    private final String FIELD_IMAGE_LINK = "mImageLink";
    private final String FIELD_STORE_ID = "mStoreId";

    private final String FIELD_ADDRESS = "mAddress";
    private final String FIELD_IMG = "mImage";

    private final String FIELD_LAT = "mLat";
    private final String FIELD_LNG = "mLng";
    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        // During a migration, a DynamicRealm is exposed. A DynamicRealm is an untyped variant of a normal Realm, but
        // with the same object creation and query capabilities.
        // A DynamicRealm uses Strings instead of Class references because the Classes might not even exist or have been
        // renamed.
        // Access the Realm schema in order to create, modify or delete classes and their fields.
        RealmSchema schema = realm.getSchema();
        /************************************************
         // Version 0
         class Person
         @Required String firstName;
         @Required String lastName;
         int    age;
         // Version 1
         class Person
         @Required String fullName;            // combine firstName and lastName into single field.
         int age;
         ************************************************/
        // Migrate from version 0 to version 1
        if (oldVersion == MIGRATION_VERSION_0) {
            schema.remove(CATEGORY_TABLE_NAME);
            RealmObjectSchema object = schema.create(CATEGORY_TABLE_NAME);
            object.addField(FIELD_ID, String.class, FieldAttribute.REQUIRED);
            object.addField(FIELD_NAME, String.class, FieldAttribute.REQUIRED);
            object.addField(FIELD_IMAGE_LINK, String.class, FieldAttribute.REQUIRED);
            object.addField(FIELD_STORE_ID, Integer.class, FieldAttribute.REQUIRED);
            object.setNullable(FIELD_ID, true);
            object.setNullable(FIELD_NAME, true);
            object.setNullable(FIELD_IMAGE_LINK, true);
            oldVersion++;
        }
        /************************************************
         // Version 2
         class Pet                   // add a new model class
         @Required String name;
         @Required String type;
         class Person
         @Required String fullName;
         int age;
         RealmList<Pet> pets;    // add an array property
         ************************************************/
        // Migrate from version 1 to version 2
        if (oldVersion == MIGRATION_VERSION_1) {
            RealmObjectSchema CommerceSchema = schema.create(COMMERCE_TABLE_NAME)
                .addField(FIELD_ID, Integer.class, FieldAttribute.REQUIRED)
                .addField(FIELD_NAME, String.class, FieldAttribute.REQUIRED)
                .addField(FIELD_ADDRESS, String.class, FieldAttribute.REQUIRED)
                .addField(FIELD_IMG, String.class, FieldAttribute.REQUIRED);
            CommerceSchema.setNullable(FIELD_NAME, true);
            CommerceSchema.setNullable(FIELD_ADDRESS, true);
            CommerceSchema.setNullable(FIELD_IMG, true);
            oldVersion++;
        }
        /************************************************
         // Version 3
         class Pet
         @Required String name;
         int type;               // type becomes int
         class Person
         String fullName;        // fullName is nullable now
         RealmList<Pet> pets;    // age and pets re-ordered (no action needed)
         int age;
         ************************************************/
        // Migrate from version 2 to version 3
        if (oldVersion == MIGRATION_VERSION_2) {
            RealmObjectSchema CommerceSchema = schema.get(COMMERCE_TABLE_NAME);
            CommerceSchema.addPrimaryKey(FIELD_ID);
            oldVersion++;
        }

        // Migrate from version 3 to version 4
        if (oldVersion == MIGRATION_VERSION_3) {
            RealmObjectSchema CommerceSchema = schema.get(COMMERCE_TABLE_NAME);
            CommerceSchema.addField(FIELD_LAT, Double.class, FieldAttribute.REQUIRED);
            CommerceSchema.addField(FIELD_LNG, Double.class, FieldAttribute.REQUIRED);
//            CommerceSchema.setNullable(FIELD_LAT, true);
//            CommerceSchema.setNullable(FIELD_LNG, true);
            oldVersion++;
        }
    }
}