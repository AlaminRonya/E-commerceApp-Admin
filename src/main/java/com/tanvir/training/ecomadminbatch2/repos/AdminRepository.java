package com.tanvir.training.ecomadminbatch2.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.tanvir.training.ecomadminbatch2.callbacks.OnCategoryQueryCompleteListener;
import com.tanvir.training.ecomadminbatch2.callbacks.OnCheckAdminListener;
import com.tanvir.training.ecomadminbatch2.callbacks.OnProductQueryCompleteListener;
import com.tanvir.training.ecomadminbatch2.callbacks.OnPurchaseQueryCompleteListener;
import com.tanvir.training.ecomadminbatch2.callbacks.OnSingleProductQueryCompleteListener;
import com.tanvir.training.ecomadminbatch2.models.ProductModel;
import com.tanvir.training.ecomadminbatch2.models.PurchaseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AdminRepository {
    private final String TAG = AdminRepository.class.getSimpleName();
    private static final String COLLECTION_ADMIN = "Admins";
    private static final String COLLECTION_CATEGORIES = "CateGories";
    private static final String COLLECTION_PRODUCT = "Products";
    private static final String COLLECTION_PURCHASE = "Purchases";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addNewProduct(ProductModel productModel, PurchaseModel purchaseModel){
        final DocumentReference pDoc = db.collection(COLLECTION_PRODUCT).document();
        productModel.setProductId(pDoc.getId());
        final DocumentReference purDoc = db.collection(COLLECTION_PURCHASE).document();
        purchaseModel.setPurchaseId(purDoc.getId());
        purchaseModel.setProductId(pDoc.getId());
        final WriteBatch writeBatch = db.batch();
        writeBatch.set(pDoc, productModel);
        writeBatch.set(purDoc, purchaseModel);
        writeBatch.commit().addOnCompleteListener(task -> {
            Log.e(TAG, "saved");
        }).addOnFailureListener(e -> {
            Log.e(TAG, "failed");

        });
    }


    public void getAllCategories(OnCategoryQueryCompleteListener listener){
        db.collection(COLLECTION_CATEGORIES).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("DATA",""+error.getLocalizedMessage());
                    return;
                }
                final List<String> temp = new ArrayList<>();
                for (DocumentSnapshot doc: value.getDocuments()){
                    temp.add(doc.get("name",String.class));
                }
                Log.e("DATA",""+temp.size());
                Collections.sort(temp);
                listener.onCategoryQueryComplete(temp);
            }
        });
    }
    public void getAllProducts(OnProductQueryCompleteListener listener){
        db.collection(COLLECTION_PRODUCT).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                final List<ProductModel> temp = new ArrayList<>();
                for (DocumentSnapshot doc: value.getDocuments()){
                    temp.add(doc.toObject(ProductModel.class));
                }
                listener.onProductQueryComplete(temp);
            }
        });
    }

    public void getAllProductsByCategory(String category, OnProductQueryCompleteListener listener) {
        db.collection(COLLECTION_PRODUCT)
                .whereEqualTo("category", category)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    final List<ProductModel> temp = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        temp.add(doc.toObject(ProductModel.class));
                    }
                    listener.onProductQueryComplete(temp);
                });
    }
    public void getPurchasesByProductId(String productId, OnPurchaseQueryCompleteListener listener) {
        db.collection(COLLECTION_PURCHASE)
                .whereEqualTo("productId", productId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    final List<PurchaseModel> temp = new ArrayList<>();
                    for (DocumentSnapshot doc : value.getDocuments()) {
                        temp.add(doc.toObject(PurchaseModel.class));
                    }
                    listener.onPurchaseQueryComplete(temp);
                });
    }

    public void getProductByProductId(String productId, OnSingleProductQueryCompleteListener listener) {
        db.collection(COLLECTION_PRODUCT).document(productId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    final ProductModel model = value.toObject(ProductModel.class);
                    listener.onSingleProductQueryComplete(model);
                });
    }

    public void checkAdmin(String uid, OnCheckAdminListener listener) {
        final boolean status;
        db.collection(COLLECTION_ADMIN)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot>
                                                   task) {
                        listener.onCheckAdmin(task.getResult().exists());
                    }
                });

    }


}
