import { createSelector } from 'reselect';

export const selectFavouritesState = (state) => state.favourites || { favourites: [], loading: false, error: null };

export const selectFavourites = createSelector(
  [selectFavouritesState],
  (favouritesState) => favouritesState.favourites || []
);

export const selectFavouritesLoading = createSelector(
  [selectFavouritesState],
  (favouritesState) => favouritesState.loading || false
);

export const selectFavouritesError = createSelector(
  [selectFavouritesState],
  (favouritesState) => favouritesState.error || null
); 