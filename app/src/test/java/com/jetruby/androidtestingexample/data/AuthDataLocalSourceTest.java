package com.jetruby.androidtestingexample.data;

import android.content.SharedPreferences;
import android.support.test.filters.SmallTest;
import android.text.TextUtils;

import com.jetruby.androidtestingexample.exception.NoSuchUserException;
import com.jetruby.androidtestingexample.rules.RxSchedulersRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Artem Leushin on 09.01.2018.
 */

@PrepareForTest({TextUtils.class})
@SmallTest
public class AuthDataLocalSourceTest {

    private static final String EMAIL = "example@jetruby.com";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

    @Rule
    public RxSchedulersRule schedulersRule = new RxSchedulersRule();

    @Rule
    public PowerMockRule rulePower = new PowerMockRule();

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    AuthDataSource authLocalStore;

    TestObserver<String> observer;

    @Before
    public void setupStaticMocks() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).then(invocation -> {
            CharSequence text = (CharSequence) invocation.getArguments()[0];
            return !(text != null && text.length() > 0);
        });

        authLocalStore = new AuthDataLocalStore(sharedPreferences);
    }


    @Test
    public void getEmail_shouldReturnEmailFromPreferences() throws Exception {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn(EMAIL);

        observer = authLocalStore.getUserEmail().test();

        observer
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete()
                .assertValue(EMAIL);
    }

    @Test
    public void getEmail_shouldThrowError_ifPreferencesEmpty() {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn("");
        
        observer = authLocalStore.getUserEmail().test();

        observer
                .assertSubscribed()
                .assertNotComplete()
                .assertError(NoSuchUserException.class);
    }

    @Test
    public void getEmail_shouldReturnString() throws Exception {
        when(sharedPreferences.getString(anyString(), anyString())).thenReturn(EMAIL);
        authLocalStore.getUserEmail().test().assertValue(EMAIL);

    }

    @Test
    public void getEmail_shouldCallSharedPreferences() {
        authLocalStore.getUserEmail().test();
        verify(sharedPreferences).getString(AuthDataSource.KEY_EMAIL, "");
    }

    @Test
    public void persistEmail_shouldCallSharedPreferences() throws Exception {
        String email = EMAIL;

        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        authLocalStore.persistEmail(email);

        verify(editor).putString(AuthDataSource.KEY_EMAIL, email);
    }

    @Test
    public void persistEmail_shouldUseEmailKey() {
        String email = EMAIL;

        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);

        authLocalStore.persistEmail(email);

        verify(editor).putString(argThat(emailStoreKey -> emailStoreKey.equals(AuthDataSource.KEY_EMAIL)), anyString());
    }

    @Test
    public void persistEmail_correctEmailPassedToPrefs() {
        String email = EMAIL;

        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);

        authLocalStore.persistEmail(email);
        verify(editor).putString(anyString(), argThat(passedEmail -> passedEmail.equals(email)));
    }

    @Test
    public void clean_shouldCallClearAndCommit() throws Exception {
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.clear()).thenReturn(editor);

        authLocalStore.clear();

        verify(editor).clear();
        verify(editor).commit();
    }
}